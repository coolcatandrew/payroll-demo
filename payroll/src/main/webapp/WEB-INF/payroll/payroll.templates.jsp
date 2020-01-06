<%--Main app view--%>
<script type="text/template" id="app-template">
    <div class="col-xs-12">
        <div class="panel panel-primary">
            <div class='panel-heading'>
                Payroll Report
                <button class="btn btn-xs btn-default pull-right close-btn" type="button" onclick="window.close(); return false;">Close</button>
            </div>
            <div class="panel-body">
                <div class="panel-wrapper">
                </div>
            </div>
        </div>
    </div>
</script>

<%--File upload and table container--%>
<script type="text/template" id="upload-file-template">
    <h5>Upload Payroll Report</h5>
    <div>
        <form method="POST" enctype="multipart/form-data" id="fileUploadForm" class="input-group">
            <input type="file" accept=".csv"
                   class="form-control file-input" name="file" title="Enter a CSV file"/>
            <span class="input-group-btn">
            <input class="btn btn-default upload-file" value="Upload"/>
            </span>
        </form>
        <div class="upload-message-group">
            <span class="ajax-message"></span>
        </div>
    </div>
    <div class="top-padded report-table"></div>
</script>

<%--Table headings--%>
<script type="text/template" id="report-table-template">
    <h5>Payroll Summary</h5>
    <table id="table-test" class="table table-striped table-hover">
        <thead>
        <tr>
            <th class="registered-name">Employee Id</th>
            <th class="registered-name">Pay Period</th>
            <th class="registered-name">Amount Paid</th>
        </tr>
        </thead>
        <tbody class="report-item-container"></tbody>
    </table>
</script>

<%--Table entries--%>
<script type="text/template" id="report-item-template">
    <td>{{=employeeId }}</td>
    <td>{{=payPeriod }}</td>
    <td>{{=amountPaid }}</td>
</script>