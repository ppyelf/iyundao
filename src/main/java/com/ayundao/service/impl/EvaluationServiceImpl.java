package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseComponent;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.ExcelUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.base.utils.TimeUtils;
import com.ayundao.entity.Evaluation;
import com.ayundao.entity.EvaluationIndex;
import com.ayundao.entity.User;
import com.ayundao.entity.UserInfo;
import com.ayundao.repository.EvaluationIndexRepository;
import com.ayundao.repository.EvaluationRepository;
import com.ayundao.service.EvaluationService;
import com.ayundao.service.UserInfoService;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * @ClassName: EvaluationServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/21 14:26
 * @Description: 实现 - 考评
 * @Version: V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationIndexRepository evaluationIndexRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Override
    public JSONArray getIndexList() {
        List<Map<String, Object>> list = evaluationRepository.getIndexList();
        JSONArray arr = new JSONArray();
        for (Map<String, Object> map : list) {
            arr.add(new JSONObject(map));
        }
        return arr;
    }

    @Override
    public Evaluation find(String id) {
        return evaluationRepository.find(id);
    }

    @Override
    public EvaluationIndex findEvaluationIndex(String id) {
        return evaluationIndexRepository.find(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Evaluation save(String year, User user, EvaluationIndex ei, double score, String remark, String number, String patientName, User operator) {
        Evaluation e = new Evaluation();
        e.setOperator(new BaseComponent(operator.getCode(), operator.getName()));
        e.setUser(user);
        e.setEvaluationIndex(ei);
        e.setYear(year);
        e.setScore(score);
        e.setRemark(remark);
        e.setNumber(number);
        e.setPatientName(patientName);
        e.setStatus(Evaluation.STATUS.waiting);
        e.setSureTime(null);
        return evaluationRepository.save(e);
    }

    @Override
    public Page<JSONObject> getList(String startTime, String endTime, String code, String subjectId, String addSubjectId, String indexId, int s, String currentSubjectId, int num, int size, String departId) {
        Pageable pageable = new Pageable(num, size);
        num = num == 0 ? 0 : num * size;
        code = "%" + code + "%";
        subjectId = "%" + subjectId + "%";
        addSubjectId = "%" + addSubjectId + "%";
        currentSubjectId = "%" + currentSubjectId + "%";
        indexId = "%" + indexId + "%";
        departId = "%" + departId + "%";
        int[] status = null;
        if (s == -1) {
            status = new int[]{0, 1, 2};
        } else {
            for (Evaluation.STATUS val : Evaluation.STATUS.values()) {
                if (val.ordinal() == s) {
                    status = new int[]{val.ordinal()};
                    break;
                }
            }
        }
        List<Map<String, Object>> list = evaluationRepository.getList(startTime, endTime, code, subjectId, addSubjectId, status, currentSubjectId, num, size, indexId, departId);
        long count = evaluationRepository.countList(startTime, endTime, code, subjectId, addSubjectId, status, currentSubjectId, indexId, departId);
        List<JSONObject> ll = new LinkedList<>();
        for (Map<String, Object> map : list) {
            ll.add(new JSONObject(map));
        }
        Page<JSONObject> page = new Page<>(ll, count, pageable);
        return page;
    }

    @Override
    public void deleteByIDS(String[] ids) {
        List<Evaluation> list = evaluationRepository.findByIds(ids);
        evaluationRepository.deleteAll(list);
    }

    @Override
    public Page<JSONObject> getSumList(String code, String year, int num, int size) {
        code = "%" + code + "%";
        year = "%" + year + "%";
        Pageable pageable = new Pageable(num, size);
        num = num == 0 ? 0 : num * size;
        List<Map<String, Object>> list = evaluationRepository.getSumList(code, year, num, size);
        List<JSONObject> result = new LinkedList<>();
        for (Map<String, Object> map : list) {
            result.add(new JSONObject(map));
        }
        List<Long> count = evaluationRepository.countSumList(code, year);
        return new Page<>(result, count.size(), pageable);
    }

    @Override
    public List<String> getYearList() {
        return evaluationRepository.getYearList();
    }

    @Override
    public JsonResult viewSum(User user, String year) {
        JsonResult jsonResult = JsonResult.success();
        JSONObject json = new JSONObject();
        UserInfo ui = userInfoService.findbyUserId(user.getId());
        if (ui == null) {
            return JsonResult.failure(603, "用户尚未添加用户详情");
        }
        json.put("name", user.getName());
        json.put("sex", user.getSex() == 0 ? "男" : "女");
        json.put("birthday", ui.getBirthday());
        json.put("postType", ui.getPostType() != null ? ui.getPostType().getName() : "其他");
        json.put("workUnit", ui.getDepartment());
        json.put("title", ui.getPost());
        json.put("post", ui.getTitle());
        year = "%" + year + "%";
        List<Map<String, Object>> list = evaluationRepository.findEvaluationByUserIdAndYear(user.getId(), year);
        JSONArray arr = new JSONArray();
        for (Map<String, Object> map : list) {
            arr.add(new JSONObject(map));
        }
        json.put("index", arr);
        jsonResult.setData(json);
        return jsonResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult upload(MultipartFile file, JsonResult success) {
        Workbook wb = ExcelUtils.getWorkBook(file);
        Sheet sheet = wb.getSheetAt(0);
        JSONArray arr = new JSONArray();
        String val = ExcelUtils.getCellValue(sheet.getRow(0).getCell(1)).toString();
        String year = val;
        if (StringUtils.isBlank(year)) {
            return JsonResult.failure(606, "年份不能为空");
        }
        String[] codes = new String[sheet.getLastRowNum() - 1];
        List<EvaluationIndex> indices = new LinkedList<>();
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) == null) {
                break;
            }
            codes[i - 2] = ExcelUtils.getCellValue(sheet.getRow(i).getCell(0)).toString();
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(2)).toString();
            for (EvaluationIndex.TYPE value : EvaluationIndex.TYPE.values()) {
                if (value.getName().substring(0, 2).equals(val.substring(1, 3))) {
                    EvaluationIndex evaluationIndex = evaluationIndexRepository.findByTypeAndName(value.ordinal(), val.substring(4));
                    if (evaluationIndex == null) {
                        return JsonResult.failure(602, "第" + (i + 1) + "行指标查询不存在");
                    }
                    indices.add(evaluationIndex);
                    break;
                }
            }

        }
        val = ExcelUtils.getCellValue(sheet.getRow(0).getCell(3)).toString();
        User aUser = userService.findByCode(val);
        if (aUser == null) {
            return JsonResult.failure(604, "录入人胸牌号不存在");
        }
        List<Evaluation> list = new ArrayList<>();
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) == null) {
                break;
            }
            Evaluation e = new Evaluation();
            e.setYear(year);
            int index = i - 2;
            User user = userService.findByCode(codes[index]);
            if (user == null) {
                return JsonResult.failure(601, "第" + (i + 1) + "行胸牌号为空或查询不存在");
            }
            e.setUser(user);
            EvaluationIndex ei = indices.get(index);
            e.setEvaluationIndex(ei);
            val = sheet.getRow(i).getCell(3).getNumericCellValue() + "";
            if (StringUtils.isBlank(val)) {
                return JsonResult.failure(603, "第" + (i + 1) + "行未设置医德分或医德分异常");
            }
            double score = Double.parseDouble(val);
            if (ei.getType().equals(EvaluationIndex.TYPE.one)) {
                e.setScore(-999.0);
            } else if (score <= ei.getMax() && score >= ei.getMin()) {
                e.setScore(score);
            } else {
                return JsonResult.failure(605, "第" + (i + 1) + "行医德分必须在" + ei.getMin() + "到" + ei.getMax() + "之间");
            }
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(4)).toString();
            e.setRemark(val);
            e.setOperator(new BaseComponent(aUser.getCode(), aUser.getName()));

            //设置病人
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(5)).toString();
            e.setNumber(val);
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(6)).toString();
            e.setPatientName(val);
            e.setStatus(Evaluation.STATUS.waiting);
            e.setSureTime(null);
            list.add(e);
        }
        for (Evaluation evaluation : list) {
            JSONObject json = JsonUtils.getJson(evaluationRepository.save(evaluation));
            arr.add(json);
        }
        JsonResult jsonResult = JsonResult.success();
        jsonResult.setData(arr);
        return jsonResult;
    }

    @Override
    public void downloadEvaluation(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 创建excel工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //第一行
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("年份:");

        //第二行
        row.createCell(2).setCellValue("录入人员胸牌号:");

        //标题行
        row = sheet.createRow(1);
        String[] names = new String[]{"胸牌号", "考评对象", "指标", "医德分", "备注", "病历号", "病人姓名"};
        List<EvaluationIndex> list = evaluationIndexRepository.getAllNames();
        String[] val = new String[list.size()];
        for (int j = 0; j < val.length; j++) {
            EvaluationIndex ei = list.get(j);
            val[j] = "(" + ei.getType().getName().substring(0, 2) + ")" + ei.getName();
        }
        for (int i = 0; i < names.length; i++) {
            row.createCell(i).setCellValue(names[i]);
            if (names[i].equals("指标")) {
                ExcelUtils.setHSSFValidation(sheet, val, 2, 100, 2, 2);
            }
        }
        ExcelUtils.setCellStyle(wb);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        // 设置response参数
        resp.reset();
        resp.setContentType("application/vnd.ms-excel;charset=utf-8");
        resp.setHeader("Content-Disposition", "attachment;filename=" + new String(("医德医风.xls").getBytes(), "iso-8859-1"));
        resp.setHeader("Access-Control-Allow-Origin", "*");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            ServletOutputStream out = resp.getOutputStream();
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }

    @Override
    public JsonResult export(String code, String year, HttpServletRequest req, HttpServletResponse resp) {
        User user = userService.findByCode(code);
        if (user == null) {
            return JsonResult.failure(602, "用户不存在");
        }
        UserInfo userInfo = userInfoService.findbyUserId(user.getId());
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph titleParagraph = doc.createParagraph();    //新建一个标题段落对象（就是一段文字）
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);//样式居中
        XWPFRun titleFun = titleParagraph.createRun();    //创建文本对象
        titleFun.setText("医务人员医德考评表"); //设置标题的名字
        titleFun.setBold(true); //加粗
        titleFun.setColor("000000");//设置颜色
        titleFun.setFontSize(25);    //字体大小
        titleFun.addBreak();    //换行

        titleParagraph = doc.createParagraph();    //新建一个标题段落对象（就是一段文字）
        titleParagraph.setAlignment(ParagraphAlignment.LEFT);//样式居中
        titleFun = titleParagraph.createRun();
        titleFun.setText("考核年度:" + year);
        titleFun.setBold(true);
        titleFun.setFontSize(12);
        titleFun.addBreak();

        //表格
        XWPFTable table = doc.createTable(3, 8);
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(new BigInteger("9000"));
        XWPFTableCell cell = null;
        table.getRow(0).getCell(0).setText("姓名");
        table.getRow(0).getCell(1).setText(user.getName());
        table.getRow(0).getCell(2).setText("性别");
        setCellWidthAndVAlign(table.getRow(0).getCell(2), 2);
        table.getRow(0).getCell(3).setText(user.getSex() == 1 ? "女" : "男");
        setCellWidthAndVAlign(table.getRow(0).getCell(3), 2);
        table.getRow(0).getCell(4).setText("出生年月");
        cell = table.getRow(0).getCell(5);
        cell.setText(userInfo.getBirthday());
        table.getRow(0).getCell(6).setText("岗位");
        table.getRow(0).getCell(7).setText(userInfo.getPostType() == null ? "无" : userInfo.getPostType().getName());
        setCellWidthAndVAlign(table.getRow(0).getCell(7), 4);

        //第二行
        table.getRow(1).getCell(0).setText("所在单位");
        cell = table.getRow(1).getCell(1);
        mergeCellsHorizontal(table, 1, 1, 3);
        cell.setText(userInfo.getDepartment());
        table.getRow(1).getCell(4).setText("职务");
        table.getRow(1).getCell(5).setText(userInfo.getPost());
        table.getRow(1).getCell(6).setText("职称");
        table.getRow(1).getCell(7).setText(StringUtils.isBlank(userInfo.getTitle()) ? "无" : userInfo.getTitle());
        setCellWidthAndVAlign(table.getRow(1).getCell(7), 4);

        //第三行
        table.getRow(2).getCtRow().addNewTrPr().addNewTrHeight().setVal(BigInteger.valueOf(6000));
        cell = table.getRow(2).getCell(0);
        cell.setText("个人评价");
        setCellWidthAndVAlign(cell, 4);
        List<Map<String, Object>> list = evaluationRepository.getEvaluationByUserIdAndYear(user.getCode(), year);

        cell = table.getRow(2).getCell(1);
        mergeCellsHorizontal(table, 2, 1, 7);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.TOP);
        XWPFParagraph para = cell.addParagraph();
        para.setAlignment(ParagraphAlignment.LEFT);//样式居中
        String val = "";
        double score = 0;
        XWPFRun run = para.createRun();
        for (Map<String, Object> map : list) {
            if (StringUtils.isBlank(val)) {
                val = map.get("year").toString();
                run.setText(val + "年度:");
            }
            run.addBreak();
            run.addTab();
            run.setText(map.get("type").toString());
            run.addTab();
            run.setText(map.get("name").toString());
            run.addTab();
            run.setText("得分:" + map.get("score").toString());
            score += Double.parseDouble(map.get("score").toString());
        }
        para = cell.addParagraph();
        para.setAlignment(ParagraphAlignment.RIGHT);
        run = para.createRun();
        run.addBreak();
        run.setText("总分:"+score);

        cell.setParagraph(para.getBody().getParagraphs().get(0));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            doc.write(os);
            ExcelUtils.createFile(user.getName() + ".doc", resp, os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                doc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sure(Evaluation evaluation, Evaluation.STATUS status) {
        evaluation.setStatus(status);
        evaluation.setSureTime(TimeUtils.convertTime(new Date(), TimeUtils.yyyyMMddHHmmss));
        evaluationRepository.save(evaluation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Evaluation save(Evaluation e) {
        return evaluationRepository.save(e);
    }

    /**
     * @Description: 设置列宽和垂直对齐方式
     */
    private void setCellWidthAndVAlign(XWPFTableCell cell, int width) {
        CTTcPr tcpr = cell.getCTTc().addNewTcPr();
        CTTblWidth w = tcpr.addNewTcW();
        w.setType(STTblWidth.DXA);
        w.setW(BigInteger.valueOf(width * 360));
    }

    private void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
}
