//Immediately invoked function expression
(function ($, window, undefined) {

    _.templateSettings = {
        interpolate: /\{\{=(.+?)\}\}/g
    };

    Backbone.Layout.configure({
        manage: true
    });

    window.PAYROLL = window.PAYROLL || {
        Views: {},
        Collections: {},
        Models: {},
        Templates: {},
        Settings: {}
    };

    var PAY = PAYROLL;

    PAY.Models.ReportEntry = Backbone.Model.extend({
        defaults: {
            reportId: '',
            employeeId: '',
            payPeriod: '',
            amountPaid: ''
        }
    });

    PAY.Collections.ReportEntries = Backbone.Collection.extend({
        model: PAY.Models.ReportEntry
    });

    PAY.Views.App = Backbone.View.extend({
        template: '#app-template',
        className: 'app-view',
        events: {
        },

        initialize: function (options) {
            this.insertView('.panel-wrapper', new PAY.Views.FileUpload({ reportEntries: options.reportEntries }));
        }
    });

    PAY.Views.FileUpload = Backbone.View.extend({
        template: '#upload-file-template',
        events: {
            'click .upload-file': 'uploadFile',
            'change .file-input': 'removeAjaxMessage'
        },

        initialize: function () {
            if (this.reportEntries.length > 0) {
                this.tableView = this.insertView('.report-table', new PAY.Views.ReportTable({ reportEntries: this.reportEntries}));
            }
        },

        uploadFile: function () {
            var form = this.$el.find('#fileUploadForm')[0];
            var data = new FormData(form);
            this.removeAjaxMessage();
            if (this.$el.find('.file-input').prop('files').length === 0) {
                this.toggleAjaxMessage('Please choose a CSV file to upload', false);
                return;
            }
            this.toggleAjaxMessage('Uploading CSV file');

            $.ajax({
                type: 'POST',
                url: 'reportCSV',
                contentType: false,
                enctype: 'multipart/form-data',
                cache: false, // Force requested pages not to be cached by the browser
                processData: false, // Avoid making query string instead of JSON
                data: data,
                success: function(result) {
                    if (result.data == null) {
                        this.toggleAjaxMessage(result.message, false);
                        return;
                    }
                    console.log(this.tableView);
                    if (this.tableView === undefined) {
                        console.log("making it");
                        this.tableView = this.insertView('.report-table', new PAY.Views.ReportTable({ reportEntries: this.reportEntries}));
                    }
                    this.reportEntries.set(result.data);
                    this.$el.find('.file-input').val('');
                    this.toggleAjaxMessage(result.message, true);
                }.bind(this),
                error: function () {
                    this.toggleAjaxMessage("Unable to upload csv. Please contact administrator", false);
                }.bind(this)
            });
        },

        toggleAjaxMessage: function (message, success) {
            var messageElem = this.$el.find('.ajax-message');
            messageElem.toggleClass('text-success', success);
            messageElem.toggleClass('text-danger', success === false);
            messageElem.text(message === undefined ? '' : message);
        },

        removeAjaxMessage: function () {
            var messageElem = this.$el.find('.ajax-message');
            messageElem.text('');
            messageElem.removeClass('text-success text-danger');
        }
    });

    PAY.Views.ReportTable = Backbone.View.extend({
        template: '#report-table-template',

        initialize: function () {
            this.listenTo(this.reportEntries, 'add', this.render);
        },

        beforeRender: function () {
            this.reportEntries.each(function(model) {
                this.addItem(model, false);
            }, this);
        },

        addItem: function (model, render) {
            var view = this.insertView('.report-item-container', new PAY.Views.ReportItem({ model: model, collection: this.reportEntries }));
            if (render !== false) {
                view.render();
            }
        }

    });

    PAY.Views.ReportItem = Backbone.View.extend({
        template: '#report-item-template',
        tagName: 'tr',

        serialize: function () {
            return this.model.toJSON();
        }

    });

//END IIFE
}) (jQuery, window, undefined);