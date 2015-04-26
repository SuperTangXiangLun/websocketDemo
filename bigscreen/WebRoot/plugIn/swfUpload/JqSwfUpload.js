/*!
 * swf上传控件
 * 创建人：qinnailin
 * 创建时间：2014/9/23
 *
 * 
 */

(function ($) {
    $.fn.extend({
        jqswfupload: function (opstions) {
            var ops = $.extend(false, {
                url: '/upload/',
                flashUrl: '/plugIn/swfUpload/swfupload.swf',
                postParams: {},
                buttonImageUrl: '/plugIn/swfUpload/btn-pload-select60.png',
                buttonText: '<span class="jquery-ui-upload-text">浏览..</span>',
                upbuttonText:'上传',
                buttonWidth: 62,
                buttonHeight: 30,
                buttonTextTopPadding:5,
                style: '.jquery-ui-upload-text {color: black; text-align:center;margin-top:15px;}',
                multiple: true,
                allowedExtensions: ["jpg", "jpeg", "gif", "png", "rar", "zip", "mov", "3gp"],
                maxsize: 0,
                minsize:0,
                fileTypesDescription: 'choose file(s)',
                fileSizeWarning: 'The limit of <strong>{limit}</strong> was reached',
                flashLoaded: function () { },
                fileDialogStart: function () { },
                fileQueue: function () { },
                fileQueueError: function () { },
                fileUploadProgress: function () { },
                fileDialogComplete: function () { },
                fileUploadSuccess: function (file, data) { },
                fileUploadComplete: function () { },
                fileUploadError: function () { },
                uploadStart: function () { },
                uploadProgress: function () { },
                uploadComplete: function () { },
                debug: false
            }, opstions || {});
            var objthis = $(this);
            var buttonid = "jquery-ui-upload-flash-button-" + Math.random();
            createuploadbox();
            //创建上传组件盒子
            function createuploadbox() {
                var temp = "<div class='fm-upload-flash-box' style='width: " + ops.buttonWidth + "px;float: left;margin-right: 5px;'>" +
                    "<span id='" + buttonid + "'></span></div><button class='btn static-upload'>" + ops.upbuttonText + "</button><ul class='qq-upload-list'></ul>";
                $(objthis).append(temp);
            }
            //绑定上传按钮
            $(objthis).on("click", ".static-upload", function () {
                swfu.startUpload();
            });
            //绑定删除按钮
            $(objthis).on("click", ".qq-upload-cancel", function () {
                var id = $(this).attr("data-id");
                var removeFile = self.getFile(id);
                self.removeFromQueue(removeFile);
                swfu.cancelUpload(id);
                $("#li_" + id).remove();
            });
            var self = {
                //文件类型转化
                concatTypes: function (types) {
                    if (typeof types == 'string') {
                        return types;
                    } else {
                        var typesCont = '';
                        $.each(types, function (i, value) {
                            typesCont += '*.' + value + '; ';
                        });
                        return typesCont.substring(0, typesCont.length - 2);
                    }
                },
                //文件大小转化
                convertSize: function (n) {
                    var s = ['B', 'KB', 'MB', 'GB'];
                    if (!Array.indexOf) {
                        Array.prototype.indexOf = function (obj) {
                            for (var i = 0; i < this.length; i++) {
                                if (this[i] == obj) {
                                    return i;
                                }
                            }
                            return -1;
                        }
                    }
                    if (typeof n == 'string') {
                        var sizeArray = n.split(' ');
                        var indexUnit = s.indexOf(sizeArray[1]);
                        return Math.round(sizeArray[0] * (Math.pow(1024, indexUnit)));

                    } else {
                        if (n) {
                            var e = Math.floor(Math.log(n) / Math.log(1024));
                            var converted = (n / Math.pow(1024, Math.floor(e))).toFixed(2);
                            return Math.ceil(converted, 2) + ' ' + s[e];
                        }
                        else {
                            return 0;
                        }
                    }


                },
                //文件截取
                fileSplit: function (word, len) {
                    var trunc = word;
                    m = trunc.match(/([^\/\\]+)\.(\w+)$/);
                    if (m[1].length > len) {
                        trunc = m[1].substring(0, len);
                        trunc = trunc.replace(/w+$/, '');
                        trunc += '(...)';
                        return trunc + '.' + m[2];
                    }
                    return trunc;

                },
                //获取文件类型
                getFileType: function (file) {
                    if (file.type == "") {
                        return file.name.match(/([^\/\\]+)\.(\w+)$/)[2];
                    } else {
                        return file.type.toLowerCase();
                    }
                },
                //获取文件
                getFile: function(id) {
                    var newFile = swfu.getFile(id);
                    var fileType = this.getFileType(newFile);
                    newFile.type = fileType;					
                    return newFile;
                },
                //过滤文件
                queueFiles: function (file) {
                    var self = this;
                    var rules = ops.allowedExtensions;
                    var hasfile = false;
                    $.each(rules, function (j, value) {
                        if (file.type == "." + value) {
                            hasfile = true;
                        }
                    });
                    if (!hasfile) {
                        alert(file.name + " 文件类型不允许！");
                        swfu.cancelUpload(file.id);
                    } else {
                        if (ops.maxsize != 0 && file.size > ops.maxsize) {
                            alert(file.name + " " + self.convertSize(file.size) + " 文件过大！");
                            swfu.cancelUpload(file.id);
                        } else if (ops.minsize != 0 && file.size < ops.minsize) {
                            alert(file.name + " " + self.convertSize(file.size) + " 文件过小！");
                            swfu.cancelUpload(file.id);
                        } else {
                            self.files.push(file);
                            addlitoitemlist(file);
                            self.progress.total += file.size;
                        }
                    }
                    //self.updateCounter();
                },
                //移除文件
                removeFromQueue: function (file) {
                    var index = -1;
                    $.each(this.files, function (i, obj) {
                        if (obj.id == file.id) {
                            index = i;
                        }
                    });

                    this.files.splice(index, 1);
                },
                //进度条
                progress: {
                    file: null,
                    fileSize: 0,
                    current: 0,
                    percent: 0,
                    total: 0
                },
                files: [],
                fileData: [],
                medias: {},
                startUpload: function (file) {
                    swfu.startUpload();
                }
            };
            var swfOptions = {
                post_params: ops.postParams,
                upload_url: ops.url,
                flash_url: ops.flashUrl,
                button_placeholder_id: buttonid,
                button_text: ops.buttonText,
                button_text_style: ops.style,
                button_text_top_padding: ops.buttonTextTopPadding,
                button_action: ops.multiple ? SWFUpload.BUTTON_ACTION.SELECT_FILES : SWFUpload.BUTTON_ACTION.SELECT_FILE,
                file_size_limit: 0,
                button_width: ops.buttonWidth,
                button_height: ops.buttonHeight,
                file_upload_limit: 0,
                file_queue_limit: 0,
                file_types: self.concatTypes(ops.allowedExtensions),
                file_types_description: ops.fileTypesDescription,
                button_image_url: ops.buttonImageUrl,
                swfupload_loaded_handler: function () {  
                    ops.flashLoaded.call(this);
                },
                file_dialog_start_handler: function () {
                    ops.fileDialogStart.call(this);
                },
                file_queued_handler: function (file) {
                    $(objthis).find(".qq-upload-success").remove();
                    if (!ops.multiple) {
                        $(objthis).find(".qq-upload-list").html("");
                        $.each(self.files, function (i, f) {
                            swfu.cancelUpload(f.id);
                        });
                    }
                    var newFile = self.getFile(file.id);
                    self.queueFiles(newFile);
                    ops.fileQueue.call(this, newFile);
                },
                file_queue_error_handler: function (file, error, msg) {
                    ops.fileQueueError.call(this, file, error, msg);
                },
                file_dialog_complete_handler: function (selected, queued, total) {
                    ops.fileDialogComplete.call(this, selected, queued, total);
                },
                upload_start_handler: function (file) {
                    ops.uploadStart.call(this, file);
                },
                upload_progress_handler: function (file, bytes, total) {
                    var spd = Math.ceil((bytes / total) * 100);
                    $("#li_" + file.id).find(".qq-progress-bar").css({ width: spd + "%" });
                    ops.fileUploadProgress.call(this, file, bytes, total);
                },
                upload_success_handler: function (file, data, response) {
                    $("#li_" + file.id).addClass("qq-upload-success");
                    $(objthis).find(".qq-upload-cancel").hide();
                    ops.fileUploadSuccess.call(this, file, stringToJSON(data), response);
                },
                upload_complete_handler: function (file) {
                    self.progress.file = file.name;
                    self.progress.current += self.progress.fileSize;
                    self.progress.percent = Math.ceil((self.progress.current / self.progress.total) * 100);
                    ops.uploadProgress.call(this, self.progress.percent, self.progress.file);
                    self.removeFromQueue(file);
                    self.startUpload(file);
                    if (!self.files.length) {
                        // force progress complete for browser issues
                        self.progress.percent = 100;
                        ops.uploadComplete.call(this, self.fileData);
                    }
                    ops.fileUploadComplete.call(this, file);
                },
                upload_error_handler: function (file, error, msg) {
                    ops.fileUploadError.call(this, file, error, msg);
                },
                debug: ops.debug
            };
            //将文件进度条加入列表中
            function addlitoitemlist(file) {
                var temp="<li id='li_"+file.id+"' >"+
            "<span class='qq-upload-file'>" + file.name + "</span>" +
            "<span class='qq-upload-size' style='display: inline;'>" + self.convertSize(file.size)+ "</span>" +
            "<a class='qq-upload-cancel' data-id='" + file.id + "' href='javascript:' >[删除]</a>" +
            "<div class='qq-progress-bar-box'>"+
            "<span class='qq-progress-bar' style='width: 0%;'></span>"+
            "</div></li>";
                $(objthis).find(".qq-upload-list").remove(".qq-upload-success");
                $(objthis).find(".qq-upload-list").append(temp);
            }
           
            var swfu = new SWFUpload(swfOptions);

            return { doupload: function () { swfu.startUpload(); } }
        }
    });
})(jQuery);