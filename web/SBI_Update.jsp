<%@ page import="com.sbi.SBI_DAO" %>
<%@ page import="com.sbi.SBI_Transaction_Executor" %><%--
  Created by IntelliJ IDEA.
  User: abhis
  Date: 26-03-2020
  Time: 10:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    SBI_DAO sbi_dao = (SBI_DAO) request.getSession().getAttribute("sbi_dao");
    try {
        sbi_dao = SBI_Transaction_Executor.generateData(sbi_dao.getUsername(), null, true);
    } catch (Exception e) {
        e.printStackTrace();
    }
    session.setAttribute("sbi_dao", sbi_dao);
    out.print(sbi_dao.getBalance());
%>
