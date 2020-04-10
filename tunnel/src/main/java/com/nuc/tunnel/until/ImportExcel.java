package com.nuc.tunnel.until;



public class ImportExcel {

    /*public static List<Employee> getBankListByExcel(InputStream in) throws Exception{
        List<Employee> employeeList = new ArrayList<>();

        //获取工作簿
        Workbook workbook = WorkbookFactory.create(in);
        if (workbook == null){
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        //遍历excel中sheet
        for (int i = 0;i<workbook.getNumberOfSheets();i++){
            sheet = workbook.getSheetAt(i);
            if (sheet == null){
                continue;
            }
            sheet.getLastRowNum();
            //遍历sheet中所有行
            for (int j = 1;j<=sheet.getPhysicalNumberOfRows();j++){
                Employee employee = new Employee();
                row = sheet.getRow(j);
                if (row == null){
                    break;
                }
                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK){
                    break;
                }
                if (row.getCell(0) != null){
                    employee.setUserName(String.valueOf(getCellValue(row.getCell(0))));
                }
                if (row.getCell(1) != null){
                    employee.setRealName(String.valueOf(getCellValue(row.getCell(1))));
                }
                if (row.getCell(2) != null){
                    employee.setTel(String.valueOf(getCellValue(row.getCell(2))));
                }
                if (row.getCell(3) != null){
                    employee.setEmail(String.valueOf(getCellValue(row.getCell(3))));
                }
                if (row.getCell(4) != null){
                    employee.setState(String.valueOf(getCellValue(row.getCell(4))));
                }
                if (row.getCell(5) != null){
                    employee.setCode(String.valueOf(getCellValue(row.getCell(5))));
                }
//                if (row.getCell(6) != null){
//                    employee.setDepartment(String.valueOf(getCellValue(row.getCell(5))));
//                }
                //把每个对象加入list
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    public static List<OrderPlan> getPlanListByExcel(InputStream in) throws Exception{
        List<OrderPlan> orderPlans = new ArrayList<>();
        //获取工作簿
        Workbook workbook = WorkbookFactory.create(in);
        if (workbook == null){
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        //遍历excel中sheet
        for (int i = 0;i<workbook.getNumberOfSheets();i++){
            sheet = workbook.getSheetAt(0);
            if (sheet == null){
                continue;
            }
            int num = sheet.getLastRowNum();
            int nums = sheet.getPhysicalNumberOfRows();
//            System.out.println(num+""+nums);
            //遍历sheet中所有行
            for (int j = 2;j<=sheet.getPhysicalNumberOfRows();j++){
                OrderPlan orderPlan = new OrderPlan();
                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
                row = sheet.getRow(j);
                if (row == null){
                    break;
                }
                if (row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK){
                    if (row.getCell(0) != null) {
                        orderPlan.setLine(String.valueOf(getCellValue(row.getCell(0))));
                    }
                    if (row.getCell(1) != null) {
                        orderPlan.setOrderNo(String.valueOf(getCellValue(row.getCell(1))));
                    }
                    if (row.getCell(2) != null) {
                        orderPlan.setBomNo(String.valueOf(getCellValue(row.getCell(2))));
                    }
                    if (row.getCell(3) != null) {
                        orderPlan.setBomDetail(String.valueOf(getCellValue(row.getCell(3))));
                    }
                    if (row.getCell(4) != null) {
                        orderPlan.setOrderNum(Integer.valueOf((String) getCellValue(row.getCell(4))));
                    }
                    if (row.getCell(5) != null) {
                        String s = String.valueOf(getCellValue(row.getCell(5)));
                        if (s.contains("/")){
                            String[] s1 =  s.split("/");
                            orderPlan.setDeliveredNum(Integer.parseInt(s1[1]));
                        }else {
                            orderPlan.setDeliveredNum(Integer.valueOf(s));
                        }
                    }
                    if (row.getCell(6) != null) {
                        orderPlan.setPlanBeginDate(sdf.parse(String.valueOf(getCellValue(row.getCell(6)))));
                    }
                    if (row.getCell(7) != null) {
                        orderPlan.setProductSaleNo(String.valueOf(getCellValue(row.getCell(7))));
                    }
                    if (row.getCell(8) != null) {
                        orderPlan.setCreateDate(sdf.parse(String.valueOf(getCellValue(row.getCell(8)))));
                    }
                    if (row.getCell(9) != null) {
                        orderPlan.setRemark(String.valueOf(getCellValue(row.getCell(9))));
                    }
                    if (row.getCell(10) != null) {
                        orderPlan.setAttention(String.valueOf(getCellValue(row.getCell(10))));
                    }
                    if (row.getCell(11) != null) {
                        orderPlan.setContainer(String.valueOf(getCellValue(row.getCell(11))));
                    }
                    if (row.getCell(12) != null && row.getCell(12).getCellType() != Cell.CELL_TYPE_BLANK) {
                        orderPlan.setShipDate(sdf.parse(String.valueOf(getCellValue(row.getCell(12)))));
                    }
                    orderPlans.add(orderPlan);
                }
                j++;
            }
        }
        return orderPlans;
    }*/


    /**
     * 对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
  /*  public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }*/
}
