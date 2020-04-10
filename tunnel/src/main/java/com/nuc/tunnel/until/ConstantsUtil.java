package com.nuc.tunnel.until;


import java.math.BigDecimal;
import java.util.*;


public class ConstantsUtil {
    public static final Boolean CONSTANT_TRUE = true;
    public static final int INFO_ALREADY_EXIST = 201;//信息已存在
    public static final Boolean CONSTANT_FALSE = false;


    public static Boolean matchEnglish(String characters){
        Boolean matchEnglish = false;
//        matchEnglish = characters.matches("\\d{1,10}[a-zA-Z]");
        matchEnglish = characters.matches("A|B");
        return matchEnglish;
    }
    public static Boolean matchEnglishA(String characters){
        Boolean matchEnglish = false;
        matchEnglish = characters.matches("\\d{1,10}[a-aA-A]");
        return matchEnglish;
    }
    public static Boolean matchEnglishB(String characters){
        Boolean matchEnglish = false;
        matchEnglish = characters.matches("\\d{1,10}[b-bB-B]");
        return matchEnglish;
    }
    public static Long getGenerator(){
        Long simple=System.currentTimeMillis();
        //三位随机数
        int random=new Random().nextInt(900)+100;//为变量赋随机值100-999;
        return Long.valueOf(simple.toString().substring(1,11)+random);
    }
    /**
     * 设备执行框次程序Excel表头内容
     * @return
     */
    public static Map<Integer, List<ExcelBean>> frameProgramHeader(){
        Map<Integer, List<ExcelBean>> headerMap = new LinkedHashMap<>();
        List<ExcelBean> excelDataList = new ArrayList<>();
        excelDataList.add(new ExcelBean("工件长度","materialLength"));
        excelDataList.add(new ExcelBean("数量","quantity"));
        excelDataList.add(new ExcelBean("已完成","cutComplete"));
        excelDataList.add(new ExcelBean("优先级","priority"));
        excelDataList.add(new ExcelBean("冲孔面","punchingSurface"));
        excelDataList.add(new ExcelBean("冲孔方式","punchingSurfaceMode"));
        excelDataList.add(new ExcelBean("扳台面","wrench"));
        excelDataList.add(new ExcelBean("扳台面与冲孔面位置关系","wrenchPunchingSurfaceRealation"));
        excelDataList.add(new ExcelBean("扳台方式","wrenchMode"));
        excelDataList.add(new ExcelBean("加工时间","productionTime"));
        excelDataList.add(new ExcelBean("托盘","tray"));
        excelDataList.add(new ExcelBean("更换托盘","changeTray"));
        excelDataList.add(new ExcelBean("喷码1","sprayCode1"));
        excelDataList.add(new ExcelBean("喷码2","sprayCode2"));
        excelDataList.add(new ExcelBean("喷码3","sprayCode3"));
        excelDataList.add(new ExcelBean("喷码4","sprayCode4"));
        excelDataList.add(new ExcelBean("喷码5","sprayCode5"));
        excelDataList.add(new ExcelBean("喷码6","sprayCode6"));
        excelDataList.add(new ExcelBean("喷码7","sprayCode7"));
        excelDataList.add(new ExcelBean("喷码8","sprayCode8"));
        excelDataList.add(new ExcelBean("喷码9","sprayCode9"));
        excelDataList.add(new ExcelBean("喷码10","sprayCode10"));
        excelDataList.add(new ExcelBean("ID","reportId"));
        excelDataList.add(new ExcelBean("原材料序号","rawMaterialSeq"));
        excelDataList.add(new ExcelBean("车型","modelDesc"));
        excelDataList.add(new ExcelBean("生产订单号","partOrder"));
        excelDataList.add(new ExcelBean("产线","productionLine"));
        excelDataList.add(new ExcelBean("工位","additionalValue"));
        excelDataList.add(new ExcelBean("物料","materialNo"));
        excelDataList.add(new ExcelBean("是否喷码","isPenMa"));
        headerMap.put(0,excelDataList);
        return headerMap;
    }
//    复制代码
    public static int find(String str,String cha,int num){
        int x=str.indexOf(cha);
        for(int i=0;i<num;i++){
            x=str.indexOf(cha,x+1);
        }
        return x;
    }

    /**
     * 保留2位小数
     * @param number
     * @return
     */
    public static Double getKeepTwo(float number){
        BigDecimal bigD = new BigDecimal(number);
        return bigD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留1位小数
     * @param number
     * @return
     */
    public static Double getKeepOne(float number){
        BigDecimal bigD = new BigDecimal(number);
        return bigD.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static List<Integer> divide(int a, int b) {

        int q = 0;
        Boolean neg = (a > 0) ^ (b > 0);
        if(a < 0)
            a = -a;
        if(b < 0)
            b = -b;
        int msb = 0;
        for(msb = 0; msb < 32; msb++) {
            if((b << msb) >= a)
                break;
        }
        for(int i = msb; i >= 0; i--) {
            if((b << i) > a)
                continue;
            q |= (1 << i);
            a -= (b << i);
        }
        if(neg)
            q= -q;

        List<Integer> trayCounts = new ArrayList<>();
        for (int i=1;i<=q;i++){
            trayCounts.add(20);
        }
        trayCounts.add(9>1&&a-20*q>0?a-20*q:a);
        return trayCounts;
    }

}
