package com.ayundao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ayundao.base.BaseComponent;
import com.ayundao.base.Page;
import com.ayundao.base.Pageable;
import com.ayundao.base.utils.ExcelUtils;
import com.ayundao.base.utils.JsonResult;
import com.ayundao.base.utils.JsonUtils;
import com.ayundao.entity.Drugs;
import com.ayundao.entity.Subject;
import com.ayundao.entity.User;
import com.ayundao.repository.DrugsRepository;
import com.ayundao.service.DrugsService;
import com.ayundao.service.SubjectService;
import com.ayundao.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: DrugsServiceImpl
 * @project: ayundao
 * @author: 念
 * @Date: 2019/11/23 11:51
 * @Description: 实现 - 药品预警
 * @Version: V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DrugsServiceImpl implements DrugsService {

    @Autowired
    private DrugsRepository drugsRepository;
    
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult upload(MultipartFile file, JsonResult jsonResult) {
        jsonResult = JsonResult.success();
        Workbook wb = ExcelUtils.getWorkBook(file);
        Sheet sheet = wb.getSheetAt(0);
        JSONArray arr = new JSONArray();
        String val = ExcelUtils.getCellValue(sheet.getRow(0).getCell(0)).toString();
        //检测机构
        Subject subject = subjectService.findByName(val);
        if (subject == null ) {
            return JsonResult.failure(602, "第1行中医院不存在,请输入正确的机构名称");
        }
        //校验日期
        val = ExcelUtils.getCellValue(sheet.getRow(1).getCell(0 )).toString();
        String time = "";
        Pattern pattern = Pattern.compile("^(((?:19|20)\\d\\d)年(0[1-9]|1[0-2])月)(.*)$");
        Matcher matcher = pattern.matcher(val);
        if (matcher.find()) {
            time = matcher.group(1);
        }else {
            return JsonResult.failure(603, "第2行情输入正确的日期格式,如:2019年09月药品使用预警（医生约谈）签到表");
        }
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {
            Drugs d = new Drugs();
            String userName = ExcelUtils.getCellValue(sheet.getRow(i).getCell(0 )).toString();
            User user = userService.findByNameAndGroupIdIsNotNull(userName);
            if ( user == null || !user.getName().equals(userName)) {
                return JsonResult.failure(604, "第" + (i + 1) + "行姓名为空,查询不存在");
            }
            d.setUser(new BaseComponent(user.getCode(), user.getName()));
            d.setSubject(new BaseComponent(subject.getCode(), subject.getName()));
            //年份
            d.setYear(time.replace("年", "").replace("月", ""));
            //药品名称
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(1)).toString();
            if (StringUtils.isBlank(val)) {
                return JsonResult.failure(605, "第" + (i + 1) + "行的药品名称不能为空");
            }
            d.setName(val);
            //备注
            val = ExcelUtils.getCellValue(sheet.getRow(i).getCell(2)).toString();
            if (val.length() > 100) {
                return JsonResult.failure(606, "第" + (i + 1) + "行的备注超过100个字符");
            }
            d.setRemark(val);
            d = drugsRepository.save(d);
            arr.add(covertDrugs(d));
        }
        jsonResult.setData(arr);
        return jsonResult;
    }

    @Override
    public JsonResult findList(String time, String code, int num, int size, JsonResult jsonResult) {
        jsonResult = JsonResult.success();
        time = "%" + time + "%";
        code = "%" + code + "%";
        Pageable pageable = new Pageable(num, size);
        num = num == 0 ? 0 : num * size;
        List<JSONObject> result = new LinkedList<>();
        List<Map<String, Object>> list = drugsRepository.findByTimeAndCodeForPage(code, time, num, size);
        long count = drugsRepository.countByTimeAndCodeForPage(code, time);
        for (Map<String, Object> map : list) {
            result.add(new JSONObject(map));
        }
        Page<JSONObject> page = new Page<>(result, count, pageable);
        jsonResult.setData(page);
        return jsonResult;
    }

    private JSONObject covertDrugs(Drugs d) {
        JSONObject json = JsonUtils.getJson(d);
        JSONObject j = new JSONObject();
        j.put("code", d.getUser().getId());
        j.put("name", d.getUser().getName());
        json.put("user", j);
        j = new JSONObject();
        j.put("code", d.getSubject().getId());
        j.put("name", d.getSubject().getName());
        json.put("subject", j);
        return json;
    }
}
