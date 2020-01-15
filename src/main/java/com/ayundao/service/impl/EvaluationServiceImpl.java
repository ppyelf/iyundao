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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @PersistenceContext
    private EntityManager em;

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
    public Evaluation save(String year, User user, EvaluationIndex ei, double score, String remark, User operator) {
        Evaluation e = new Evaluation();
        e.setOperator(new BaseComponent(operator.getCode(), operator.getName()));
        e.setUser(user);
        e.setEvaluationIndex(ei);
        e.setYear(year);
        e.setScore(score);
        e.setRemark(remark);
        e.setStatus(Evaluation.STATUS.waiting);
        e.setSureTime(null);
        return evaluationRepository.save(e);
    }

    @Override
    public Page<JSONObject> getList(String startTime, String endTime, String code, String addSubjectId, String departId, String indexId, int s, int num, int size) {
        Pageable pageable = new Pageable(num, size);
        num = num == 0 ? 0 : num * size;
        code = "%" + code + "%";
        addSubjectId = "%" + addSubjectId + "%";
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
        List<Map<String, Object>> list = evaluationRepository.getList(startTime, endTime, code, addSubjectId, departId, indexId, status, num, size);
        long count = evaluationRepository.countList(startTime, endTime, code, addSubjectId, departId, indexId, status);
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
    public Page<JSONObject> getSumList(String code, String year, int num, int size, String order) {
        Pageable pageable = new Pageable(num, size);
        String sql = "select te.ID                                  id, " +
                "       te.YEAR                                year, " +
                "       tu.CODE                                code, " +
                "       tu.NAME                                name, " +
                "       if(tu.SEX = 0, '男', '女')               sex, " +
                "       tui.BIRTHDAY                           birthday, " +
                "       case " +
                "           when tui.POSTTYPE = 0 then '医生' " +
                "           when tui.POSTTYPE = 1 then '护士' " +
                "           when tui.POSTTYPE = 2 then '医技' " +
                "           else '其他' end                      postType, " +
                "       ifnull(tui.DEPARTMENT, tui.BRANCHNAME) department, " +
                "       tui.post                               post, " +
                "       tui.title                              title, " +
                "       sum(te.SCORE)                          score, " +
                "       td.ID                                  departId, " +
                "       td.NAME                                departName, " +
                "       tg.ID                                  groupId, " +
                "       tg.NAME                                groupName " +
                "from t_evaluation te " +
                "         left join t_evaluation_index tei on te.EVALUATIONINDEXID = tei.ID " +
                "         left join t_user tu on te.USERID = tu.ID " +
                "         left join t_user_info tui on tui.USERID = te.USERID " +
                "         left join t_user_relations tur on te.USERID = tur.USERID " +
                "         left join t_depart td on td.ID = tur.DEPARTID " +
                "         left join t_groups tg on tg.ID = tur.GROUPSID " +
                "where te.YEAR like ?2 " +
                "  and tu.CODE like ?1 " +
                "group by te.USERID " +
                "order by  " + order + " ";
        code = "%" + code + "%";
        year = "%" + year + "%";
        num = num == 0 ? 0 : num * size;
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, code);
        query.setParameter(2, year);
        query.setFirstResult(num);
        query.setMaxResults(size);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.getResultList();
        List<JSONObject> result = new LinkedList<>();
        int index = 0;
        for (Map<String, Object> map : list) {
            result.add(index++, new JSONObject(map));
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
    public JsonResult upload(MultipartFile file, User aUser, JsonResult success) {
        Workbook wb = ExcelUtils.getWorkBook(file);
        Sheet sheet = wb.getSheetAt(0);

        List<EvaluationIndex> indexes = evaluationIndexRepository.findAll();
        JSONArray arr = new JSONArray();
        String val = ExcelUtils.getCellValue(sheet.getRow(0).getCell(0)).toString();
        String year = "";
        Pattern pattern = Pattern.compile("(((?:19|20)\\d\\d)年(0[1-9]|1[0-2])月)(.*)");
        Matcher matcher = pattern.matcher(val);
        if (matcher.find()) {
            year = matcher.group(1).replace("年", "").substring(0, 6);
        } else {
            return JsonResult.failure(603, "第1行情输入正确的日期格式,如:2019年01月医德医风考评");
        }
        String[] names = new String[sheet.getLastRowNum() - 1];
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) == null) {
                break;
            }
            names[i - 2] = ExcelUtils.getCellValue(sheet.getRow(i).getCell(0)).toString();
        }
        List<Evaluation> list = new ArrayList<>();
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) == null) {
                break;
            }
            Evaluation e = new Evaluation();
            e.setYear(year);
            int index = i - 2;
            User user = userService.findByName(names[index]);
            if (user == null) {
                return JsonResult.failure(601, "第" + (i + 1) + "行用户姓名为空或查询不存在");
            }
            e.setUser(user);
            EvaluationIndex ei = null;
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(1)).toString();
            for (EvaluationIndex evaluationIndex : indexes) {
                if (evaluationIndex.getName().equals(val.substring(4))) {
                    ei = evaluationIndex;
                    break;
                }
            }
            if (ei == null) {
                return JsonResult.failure(602, "第" + (i + 1) + "行指标查询不存在");
            }
            e.setEvaluationIndex(ei);
            sheet.getRow(i).getCell(2).setCellType(CellType.STRING);
            val = sheet.getRow(i).getCell(2).getStringCellValue() + "";
            if (StringUtils.isBlank(val)) {
                return JsonResult.failure(603, "第" + (i + 1) + "行未设置医德分或医德分异常");
            }
            double score = Double.parseDouble(val);
            if (ei.getType().equals(EvaluationIndex.TYPE.one)) {
                e.setScore(-999.0);
            } else if ((score <= ei.getMax() && score >= ei.getMin()) || (score < 0 && score >= ei.getMax() && score <= ei.getMin())) {
                e.setScore(score);
            } else {
                return JsonResult.failure(605, "第" + (i + 1) + "行医德分必须在" + ei.getMin() + "到" + ei.getMax() + "之间");
            }
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(3)).toString();
            e.setRemark(val);
            e.setOperator(new BaseComponent(aUser.getCode(), aUser.getName()));

            e.setStatus(Evaluation.STATUS.agree);
            e.setSureTime(TimeUtils.convertTime(new Date(), TimeUtils.yyyyMMddHHmmss));
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
        row.createCell(0).setCellValue("____年__月医德医风考评");
        row.setHeight((short) 600);

        //标题行
        row = sheet.createRow(1);
        String[] names = new String[]{"考评对象", "指标", "医德分", "备注"};
        List<EvaluationIndex> list = evaluationIndexRepository.getAllNames();
        String[] val = new String[list.size()];
        for (int j = 0; j < val.length; j++) {
            EvaluationIndex ei = list.get(j);
            val[j] = "(" + ei.getType().getName().substring(0, 2) + ")" + ei.getName();
        }
        for (int i = 0; i < names.length; i++) {
            row.createCell(i).setCellValue(names[i]);
            if (names[i].equals("指标")) {
                ExcelUtils.setHSSFValidation(sheet, val, 2, 2, 1, 1);
            }
        }
        ExcelUtils.setCellStyle(wb);
        CellRangeAddress range = new CellRangeAddress(0, 0, 0, 3);
        sheet.addMergedRegion(range);
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
    public JsonResult export(String code, HttpServletRequest req, HttpServletResponse resp) {
        // 创建excel工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //第一行
        List<Cell> cells = new ArrayList<>();
        Row row = sheet.createRow(0);
        row.setHeight((short) 700);
        CellRangeAddress range = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(range);
        Cell cell = row.createCell(0);
        cell.setCellValue("医德医风考核档案");
        cells.add(row.getCell(0));

        //第二行
        range = new CellRangeAddress(1, 1, 0, 5);
        sheet.addMergedRegion(range);
        sheet.createRow(1).createCell(0).setCellValue("工作人员基本情况");
        sheet.getRow(1).setHeight((short) 700);
        cells.add(sheet.getRow(1).getCell(0));

        //第三行
        User user = userService.findByCode(code);
        if (user == null) {
            return JsonResult.failure(602, "用户不存在");
        }
        UserInfo userInfo = userInfoService.findbyUserId(user.getId());

        int index = 0;
        row = sheet.createRow(2);
        row.createCell(index++).setCellValue("姓名");
        row.createCell(index++).setCellValue(user.getName());
        row.createCell(index++).setCellValue("性别");
        row.createCell(index++).setCellValue(user.getSex() == 1 ? "女" : "男");
        row.createCell(index++).setCellValue("出生年月");
        row.createCell(index++).setCellValue(userInfo.getBirthday());
        cells.add(row.getCell(0));
        cells.add(row.getCell(2));
        cells.add(row.getCell(4));


        //第四行
        index = 0;
        row = sheet.createRow(3);
        row.createCell(index++).setCellValue("岗位");
        range = new CellRangeAddress(3, 3, 1, 5);
        sheet.addMergedRegion(range);
        row.createCell(index++).setCellValue(userInfo.getPostType() != null ? userInfo.getPostType().getName() : "");
        cells.add(row.getCell(0));

        //第五行
        index = 0;
        row = sheet.createRow(4);
        row.createCell(index++).setCellValue("职务");
        row.createCell(index++);
        row.createCell(index++);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 2));
        row.getCell(1).setCellValue(userInfo.getPost());
        row.createCell(index++).setCellValue("职称");
        row.createCell(index++);
        row.createCell(index++);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 5));
        row.getCell(4).setCellValue(userInfo.getTitle());
        cells.add(row.getCell(0));
        cells.add(row.getCell(3));

        //第六行
        index = 0;
        row = sheet.createRow(5);
        row.createCell(index++).setCellValue("所在科室");
        row.createCell(index);
        range = new CellRangeAddress(5, 5, 1, 5);
        sheet.addMergedRegion(range);
        row.getCell(index).setCellValue(userService.findUserDepart(user.getId()));
        cells.add(row.getCell(0));

        //第七行
        row = sheet.createRow(6);
        row.createCell(0).setCellValue("医德医风考评汇总表");
        range = new CellRangeAddress(6, 6, 0, 5);
        sheet.addMergedRegion(range);
        cells.add(row.getCell(0));

        //第八行 年份    得分	加分情况	减分情况	备注	总分
        index = 0;
        row = sheet.createRow(7);
        row.createCell(index++).setCellValue("年份");
        row.createCell(index++).setCellValue("指标类型");
        row.createCell(index++).setCellValue("指标名称");
        row.createCell(index++).setCellValue("得分");
        row.createCell(index++).setCellValue("备注");
        row.createCell(index++).setCellValue("总分");
        index=0;
        cells.add(row.getCell(index++));
        cells.add(row.getCell(index++));
        cells.add(row.getCell(index++));
        cells.add(row.getCell(index++));
        cells.add(row.getCell(index++));
        cells.add(row.getCell(index++));

        List<Map<String, Object>> list = evaluationRepository.getEvaluationByUserIdAndYear(user.getCode());
        String year = "";
        int start = 8;
        double score = 0.0;
        for (int i = 8; i < list.size() + 8; i++) {
            index = 0;
            Map<String, Object> map = list.get(i - 8);
            row = sheet.createRow(i);
            row.createCell(index++).setCellValue(map.get("year").toString().substring(0, 4));
            row.createCell(index++).setCellValue(map.get("type").toString());
            row.createCell(index++).setCellValue(map.get("name").toString());
            row.createCell(index++).setCellValue(map.get("score").toString());
            row.createCell(index++).setCellValue(StringUtils.isBlank(map.get("remark").toString()) ? "" : map.get("remark").toString());
            score += Double.parseDouble(map.get("score").toString());
            row.createCell(index++).setCellValue(score);
            if (StringUtils.isBlank(year)) {
                year = sheet.getRow(8).getCell(0).getStringCellValue();
            }
            if (!sheet.getRow(i).getCell(0).getStringCellValue().equals(year)
            || list.size() == 1 || i == (list.size() + 7)) {
                if (i == start) {
                    sheet.getRow(start).getCell(5).setCellValue(score+80);
                    continue;
                }
                range = new CellRangeAddress(start, i, 0, 0);
                sheet.addMergedRegion(range);
                int rowLine = start == 8 ? start : start - 1;
                sheet.getRow(rowLine).getCell(0).setCellValue(year);
                year = map.get("year").toString();
                range = new CellRangeAddress(start, i, 5, 5);
                sheet.addMergedRegion(range);
                sheet.getRow(start).getCell(5).setCellValue(score+80);
                score = 0.0;
                start = i + 1;
            }
        }

        ExcelUtils.setCellStyle(wb);
        //批量设置加粗的cell
        ExcelUtils.setTitleStyle(cells, wb);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            ExcelUtils.createFile(user.getName() + ".xls", resp, os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sure(List<Evaluation> evaluations, Evaluation.STATUS status) {
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getStatus().equals(status)) {
                continue;
            }
            evaluation.setStatus(status);
            evaluation.setSureTime(TimeUtils.convertTime(new Date(), TimeUtils.yyyyMMddHHmmss));
            evaluationRepository.save(evaluation);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Evaluation save(Evaluation e) {
        return evaluationRepository.save(e);
    }

    @Override
    public List<Evaluation> findByIds(String[] ids) {
        return evaluationRepository.findByIds(ids);
    }

    @Override
    public JsonResult exportYear(String year, HttpServletResponse resp, HttpServletRequest req) {
        HSSFWorkbook wb = createWorkBook(year);
        Sheet sheet = wb.getSheetAt(0);

        //年度数据
        int index = 0;
        List<Map<String, Object>> list = evaluationRepository.exportYear(year);
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                Row row = sheet.createRow(i + 3);
                //姓名
                row.createCell(index++).setCellValue(map.get("name").toString());
                //性别
                row.createCell(index++).setCellValue(map.get("sex").toString());
                //所属科室
                row.createCell(index++).setCellValue(map.get("department").toString());
                //岗位
                row.createCell(index++).setCellValue(map.get("postType").toString());
                //职务
                row.createCell(index++).setCellValue(map.get("post").toString());
                //职称
                row.createCell(index++).setCellValue(map.get("title").toString());
                //加减分
                row.createCell(index++).setCellValue(map.get("score").toString());
                //基础分
                row.createCell(index++).setCellValue(map.get("baseScore").toString());
                //总分
                row.createCell(index++).setCellValue(map.get("total").toString());
                index = 0;
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            ExcelUtils.createFile(year+"年度医德医风汇总.xls", resp, os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private HSSFWorkbook createWorkBook(String year) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        //第一行
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("富阳区第一人民医院");
        CellRangeAddress range = new CellRangeAddress(0, 0, 0, 8);
        sheet.addMergedRegion(range);
        row = sheet.createRow(1);
        row.createCell(0).setCellValue(year+ "年度全院职工医德医风汇总");
        range = new CellRangeAddress(1, 1, 0, 8);
        sheet.addMergedRegion(range);

        //第二行
        row = sheet.createRow(2);
        int i = 0;
        row.createCell(i++).setCellValue("姓名");
        row.createCell(i++).setCellValue("性别");
        row.createCell(i++).setCellValue("所属科室");
        row.createCell(i++).setCellValue("岗位");
        row.createCell(i++).setCellValue("职务");
        row.createCell(i++).setCellValue("职称");
        row.createCell(i++).setCellValue("加减分");
        row.createCell(i++).setCellValue("基础分");
        row.createCell(i++).setCellValue("总分");

        ExcelUtils.setCellStyle(wb);
        row = sheet.getRow(0);
        Cell cell = row.getCell(0);
        CellStyle cs = wb.createCellStyle();
        HSSFFont f = wb.createFont();
        f.setFontName("宋体");
        f.setFontHeightInPoints((short) 20);
        f.setBold(true);
        cs.setFont(f);
        cs.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        cs.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        cs.setWrapText(true);//自动换行
        row.setHeight((short) 600);
        sheet.setColumnWidth(0, 4500);
        cell.setCellStyle(cs);
        row = sheet.getRow(1);
        row.setHeight((short) 600);
        row.getCell(0).setCellStyle(cs);
        return wb;
    }


}
