<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payroll</title>

    <!-- Bootstrap -->
    <link href="<c:url value="/libraries/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/libraries/css/bootstrap-theme.css" />" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="<c:url value="/libraries/css/font-awesome.css" />" rel="stylesheet">

    <!-- App -->
    <link href="<c:url value="/resources/payroll.css" />" rel="stylesheet">
</head>
<body>
    <!-- App Templates -->
    <jsp:include page="payroll.templates.jsp"/>

    <!-- JQuery -->
    <script src="<c:url value="/libraries/js/jquery-3.4.1.min.js" />"></script>

    <!-- Bootstrap -->
    <script src="<c:url value="/libraries/js/bootstrap.js" />"></script>

    <!-- BackBone -->
    <script src="<c:url value="/libraries/js/underscore-min.js" />"></script>
    <script src="<c:url value="/libraries/js/backbone-min.js" />"></script>
    <script src="<c:url value="/libraries/js/backbone.layoutmanager-1.1.0.js" />"></script>

    <!-- App -->
    <script src="<c:url value="/resources/payroll.js" />"></script>

    <%-- App container--%>
    <div id="app"></div>

    <!-- App Init -->
    <script type="text/javascript">
        window.PAYROLL = window.PAYROLL || {
            Views: {},
            Collections: {},
            Models: {},
            Templates: {},
            Settings: {}
        };

        $(document).ready(function () {
            //Init app
            window.appView = new PAYROLL.Views.App({
                reportEntries: new PAYROLL.Collections.ReportEntries(${payrolls})
            });
            //Add app to dom when ready
            appView.render().promise().done(function () {
                $('#app').append(appView.$el);
            })
        });
    </script>

</body>
</html>
