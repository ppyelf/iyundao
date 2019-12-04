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
import com.ayundao.entity.*;
import com.ayundao.repository.ActionRepository;
import com.ayundao.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ActionServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/28 11:18
 * @Description: 实现 - 春风行动
 * @Version: V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionRepository actionRepository;
    
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private EvaluationService evaluationService;

    @Override
    public Page<Action> findPage(Pageable pageable) {
        return actionRepository.findPage(pageable);
    }

    @Override
    public void downloadExcel(HttpServletRequest req, HttpServletResponse resp) {
        HSSFWorkbook wb = createWorkBook();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            ExcelUtils.createFile("春风行动模板.xls", resp, os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private HSSFWorkbook createWorkBook() {
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        //第一行
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("富阳区第一人民医院");
        CellRangeAddress range = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(range);

        //第二行
        row = sheet.createRow(1);
        int i = 0;
        row.createCell(i++).setCellValue("胸牌号");
        row.createCell(i++).setCellValue("姓名");
        row.createCell(i++).setCellValue("部门编号");
        row.createCell(i++).setCellValue("部门");
        row.createCell(i++).setCellValue("金额");
        row.createCell(i++).setCellValue("爱心公益得分");

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
        return wb;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult upload(MultipartFile file, User operator) {
        JsonResult jsonResult = JsonResult.success();
        Workbook wb = ExcelUtils.getWorkBook(file);
        Sheet sheet = wb.getSheetAt(0);

        String val = "富阳区第一人民医院";
        Subject subject = subjectService.findByName(val);
        if (subject == null) {
            JsonResult.failure(601, "第1行医院名称为空或不存在");
        }
        EvaluationIndex ei = evaluationService.findEvaluationIndex("5d28f98ba17c49f6bc1c335f8866d653");

        JSONArray arr = new JSONArray();
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Action action = new Action();
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(0)).toString();
            User user = userService.findByCode(val);
            if (user == null) {
                return JsonResult.failure(602, "第"+(i+1)+"行胸牌号不能为空或不存在");
            }

            action.setUser(new BaseComponent(user.getCode(), user.getName()));
            //机构
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(2)).toString();
            Groups groups = groupsService.findByCode(val);
            if (groups == null) {
                return JsonResult.failure(603, "第"+(i+1)+"部门编号不能为空");
            }
            action.setGroup(new BaseComponent(groups.getCode(), groups.getName()));
            action.setSubject(new BaseComponent(subject.getCode(), subject.getName()));

            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(4)).toString();
            if (!val.matches("^[1-9]\\d*$")) {
                return JsonResult.failure(604, "第"+(i+1)+"请输入大于零的正整数");
            }

            //保存爱心公益指标
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(5)).toString();
            if (!val.matches("^[0-6]+([.]{1}[0-5]{1,2})?$")) {
                return JsonResult.failure(604, "第"+(i+1)+"行请输0.5-6之间的分数");
            }
            double score = Double.parseDouble(val);
            if (score > 6 || score < 0.5) {
                return JsonResult.failure(604, "第"+(i+1)+"行请输0.5-6之间的分数");
            }
            Evaluation e = new Evaluation();
            String time = TimeUtils.convertTime(new Date(), TimeUtils.yyyyMMddHHmmss);
            e.setSureTime(time);
            e.setStatus(Evaluation.STATUS.agree);
            e.setYear(time.substring(0, 4));
            e.setUser(user);
            e.setEvaluationIndex(ei);
            e.setScore(score);
            e.setOperator(new BaseComponent(operator.getCode(), operator.getName()));
            e = evaluationService.save(e);

            action.setEvaluation(e);
            action.setMoney(Long.parseLong(val));
            action = actionRepository.save(action);
            arr.add(covert(action, score));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    @Override
    public JSONObject covert(Action action, double score) {
        JSONObject result = JsonUtils.getJson(action);
        JSONObject json = new JSONObject();
        json.put("name", action.getUser().getName());
        json.put("code", action.getUser().getId());
        result.put("user", json);
        json = new JSONObject();
        json.put("code", action.getSubject().getId());
        json.put("name", action.getSubject().getName());
        result.put("subject", json);
        json = new JSONObject();
        json.put("code", action.getGroup().getId());
        json.put("name", action.getGroup().getName());
        result.put("groups", json);
        result.put("score", score);
        return result;
    }

    @Override
    public JsonResult export(String id, String year, List<Map<String, Object>> list, HttpServletRequest req, HttpServletResponse resp) {
        HSSFWorkbook wb = createWorkBook();
        Sheet sheet = wb.getSheetAt(0);

        //第一行
        Subject subject = subjectService.findByCode(id);
        if (subject == null) {
            return JsonResult.failure(602, "机构不存在");
        }
        sheet.getRow(0).getCell(0).setCellValue(subject.getName());
        
        //数据
        int index = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                sheet.createRow(i+2).createCell(index++).setCellValue(map.get("userCode").toString());
                sheet.getRow(i+2).createCell(index++).setCellValue(map.get("userName").toString());
                sheet.getRow(i+2).createCell(index++).setCellValue(map.get("groupCode").toString());
                sheet.getRow(i+2).createCell(index++).setCellValue(map.get("groupName").toString());
                sheet.getRow(i+2).createCell(index++).setCellValue(map.get("money").toString());
                sheet.getRow(i+2).createCell(index++).setCellValue(map.get("score").toString());
                index = 0;
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            ExcelUtils.createFile("春风行动"+year+".xls", resp, os);
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

    @Override
    public List<Map<String, Object>> findBySubjectIdAndYear(String id, String year) {
        year = "%" + year + "%";
        return actionRepository.findBySubjectIdAndYear(id, year);
    }


}
