function Paginat(object) {

    var totalNum = object.dataTotal;
    var pageSize = Number(object.pageAmount) || 20;
    var pageNum = Math.ceil(object.dataTotal / object.pageAmount);
    var selectSize = object.pageAmountList || [10, 20];
    var PagingFlage = true;
    var PagingNow = object.curPage || 1;
    var pagerCount = object.pageSize
    var PagingDevice = document.getElementById(object.target);
    var PagingDeviceHtml = '';
    var PagingNumHtml = '';
    getDataFlage = true;
    PagingDeviceHtml += "<div>" +
        "<div class='PagingTotalNum PagingDevice'  >共 " + totalNum +
        " 条</div><div class='PagingDevice'><div id='PagingSizeSelect' class='PagingSizeSelect'><div id='PagingSizeSelectImg' class='PagingSizeSelectImg'><i id='iconfont' class='iconfont icon-xiajiantou'></i></div> ";
    PagingDeviceHtml +=
        "<ul id='PagingSizeSelectUl'>"
    selectSize.map(function (res, index) {
        if (res == pageSize) {
            PagingDeviceHtml += "<li data-num=" + res + " style='color:#409eff;background:#ddd;'>" + res +
                "条/页</li>"
        } else {
            PagingDeviceHtml += "<li data-num=" + res + ">" + res + "条/页</li>"
        }
    })
    PagingDeviceHtml += "<div class='ulTriangle'><div/>  </ul>";
    PagingDeviceHtml += "<div class='PagingDevice' id='PagingSize'>" + pageSize + "</div>条/页</div></div>";
    PagingDeviceHtml +=
        "<div class='PagingDevice'><span  class='PagingPrev' data-pagingId='" + (((Number(PagingNow) - 1) > 0 ? (
            Number(
                PagingNow) - 1) : 1)) +
        "'><i    class='iconfont icon-zuojiantou'></i></span></div><div class='PagingDevice' id='PagingNum'>";
    for (var i = 1; i <= pageNum; i++) {
        if (i <= pagerCount - 1) {
            PagingDeviceHtml += "<span data-pagingId = '" + i + "'>" + i + "</span>";
        }
        if (i == pagerCount) {
            if (pageNum == pagerCount) {
                PagingDeviceHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum + "</span>";
            } else {
                PagingDeviceHtml += "<span data-pagingId='" + ((Number(PagingNow) + 5) < pageNum ? (Number(
                        PagingNow) +
                    5) : pageNum) + "'><i id='PagingIconRightMore'   class='iconfont icon-more'></i></span>";
                PagingDeviceHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum + "</span>";
            }
        }
    }
    PagingDeviceHtml += "</div><div class='PagingDevice'><span data-pagingId='" + (((Number(PagingNow) + 1) <
            pageNum ?
            (Number(PagingNow) + 1) : pageNum)) +
        "' class='PagingNext'><i id='iconfont' class='iconfont icon-youjiantou'></i></span></div>";
    PagingDeviceHtml += "<div class='PagingDevice'>前往<input id='PagingNow' value=" + PagingNow + ">页<div/>";
    PagingDevice.innerHTML = PagingDeviceHtml


    if (PagingNow <= pagerCount && pageNum <=
        pagerCount) {

        for(var i = 1; i <= pageNum; i ++){
            PagingNumHtml += "<span data-pagingId = '" + i  + "'>" + i  + "</span>";
        }
        // PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
        // PagingNumHtml += "<span data-pagingId = '" + 2 + "'>" + 2 + "</span>";
        // PagingNumHtml += "<span data-pagingId = '" + 3 + "'>" + 3 + "</span>";
        // PagingNumHtml += "<span data-pagingId = '" + 4 + "'>" + 4 + "</span>";
        // PagingNumHtml += "<span data-pagingId = '" + 5 + "'>" + 5 + "</span>";
        // PagingNumHtml += "<span data-pagingId = '" + 6 + "'>" + 6 + "</span>";
        // PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
        //     "</span>";
    }
    if (PagingNow < 5 && pageNum > pagerCount) {
        PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + 2 + "'>" + 2 + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + 3 + "'>" + 3 + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + 4 + "'>" + 4 + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + 5 + "'>" + 5 + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + 6 + "'>" + 6 + "</span>";
        PagingNumHtml +=
            "<span data-pagingId='" + ((Number(PagingNow) + 5) < pageNum ? (Number(
                PagingNow) + 5) : pageNum) +
            "'><i id='PagingIconRightMore'  class='iconfont icon-more'></i></span>"
        PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
            "</span>";

    }
    if (PagingNow >= 5 && pageNum > 7 &&
        PagingNow < (pageNum - 3)) {
            
        PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
        PagingNumHtml +=
            "<span data-pagingId='" + ((Number(PagingNow) - 5) >= 1 ? (Number(PagingNow) -
                5) : 1) +
            "'><i id='PagingLeftIconMore'  class='iconfont icon-more'></i></span>"
        PagingNumHtml += "<span data-pagingId = '" + (Number(PagingNow) - 2) + "'>" + (Number(PagingNow) - 2) + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + (Number(PagingNow) - 1) + "'>" + (Number(PagingNow) - 1) + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + (Number(PagingNow)) + "'>" + Number(PagingNow) + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + (Number(PagingNow) + 1) + "'>" + (Number(PagingNow) + 1) + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + (Number(PagingNow) + 2) + "'>" + (Number(PagingNow) + 2) + "</span>";
        PagingNumHtml +=
            "<span data-pagingId='" + ((Number(PagingNow) + 5) < pageNum ? (Number(
                PagingNow) + 5) : pageNum) +
            "'><i id='PagingIconRightMore'  class='iconfont icon-more'></i></span>"
        PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
            "</span>";

    }
    if (PagingNow >= (pageNum - 3) && pageNum > 7) {
        PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
        PagingNumHtml +=
            "<span data-pagingId='" + ((Number(PagingNow) - 5) >= 1 ? (Number(PagingNow) -
                5) : 1) +
            "'><i id='PagingLeftIconMore'  class='iconfont icon-more'></i></span>"
        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 5) + "'>" + (
            pageNum - 5) + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 4) + "'>" + (
            pageNum - 4) + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 3) + "'>" + (
            pageNum - 3) + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 2) + "'>" + (
            pageNum - 2) + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 1) + "'>" + (
            pageNum - 1) + "</span>";
        PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
            "</span>";
    }
    document.getElementById('PagingNum').innerHTML = PagingNumHtml
    function Handle() {
        PagingNumHtml = ''
        var PagingNum = Array.prototype.slice.call(PagingDevice.getElementsByTagName(
            'span'));

        PagingNum.map(function (result, index) {
            result.addEventListener('mouseover', function () {
                result.style.color = '#409eff'
                // document.getElementsByClassName('PagingNext')[0].style.cursor = 'pointer';
                // document.getElementsByClassName('PagingPrev')[0].style.cursor = 'pointer';
                if (result.getAttribute('data-pagingId') == PagingNow) {
                    result.style.cursor = "not-allowed";
                } else {
                    result.style.cursor = "pointer";
                }
            }, false)
            result.addEventListener('mouseout', function () {
                if (this.getAttribute('data-pagingId') != PagingNow || result.getAttribute('class') ==
                    "PagingPrev" || result.getAttribute('class') == "PagingNext") {
                    result.style.color = 'black'
                }
            }, false)
            if (PagingNow == result.getAttribute('data-pagingId') && result.getAttribute('class') !=
                "PagingPrev" && result.getAttribute('class') != "PagingNext") {
                result.style.color = '#409eff'
            }
            if (result.getAttribute('data-pagingId') != null) {
                result.onclick = function () {
                    if (result.getAttribute('data-pagingId') == PagingNow) {
                        result.style.cursor = "not-allowed";
                        return
                    } else {
                        result.style.cursor = "pointer";
                    }
                    document.getElementById('PagingNow').setAttribute('value', (result.getAttribute(
                        'data-pagingId')))
                    document.getElementById('PagingNow').value = (result.getAttribute(
                        'data-pagingId'))
                    PagingNow = result.getAttribute('data-pagingId');
                    object.getPage(PagingNow)
                    result.style.color = '#409eff'
                    if (PagingNow <= pagerCount && pageNum <=
                        pagerCount) {
                            for(var i = 1; i <= pageNum; i ++){
                                PagingNumHtml += "<span data-pagingId = '" + i  + "'>" + i  + "</span>";
                            }
                        // PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
                        // PagingNumHtml += "<span data-pagingId = '" + 2 + "'>" + 2 + "</span>";
                        // PagingNumHtml += "<span data-pagingId = '" + 3 + "'>" + 3 + "</span>";
                        // PagingNumHtml += "<span data-pagingId = '" + 4 + "'>" + 4 + "</span>";
                        // PagingNumHtml += "<span data-pagingId = '" + 5 + "'>" + 5 + "</span>";
                        // PagingNumHtml += "<span data-pagingId = '" + 6 + "'>" + 6 + "</span>";
                        // PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
                        //     "</span>";

                    }
                    if (PagingNow < 5 && pageNum > pagerCount) {
                        PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + 2 + "'>" + 2 + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + 3 + "'>" + 3 + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + 4 + "'>" + 4 + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + 5 + "'>" + 5 + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + 6 + "'>" + 6 + "</span>";
                        PagingNumHtml +=
                            "<span data-pagingId='" + ((Number(PagingNow) + 5) < pageNum ? (Number(
                                PagingNow) + 5) : pageNum) +
                            "'><i id='PagingIconRightMore'  class='iconfont icon-more'></i></span>"
                        PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
                            "</span>";
                    }
                    if (PagingNow >= 5 && pageNum > 7 &&
                        PagingNow < (pageNum - 3)) {
                        PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
                        PagingNumHtml +=
                            "<span data-pagingId='" + ((Number(PagingNow) - 5) >= 1 ? (Number(PagingNow) -
                                5) : 1) +
                            "'><i id='PagingLeftIconMore'  class='iconfont icon-more'></i></span>"
                        PagingNumHtml += "<span data-pagingId = '" + (result.getAttribute(
                            'data-pagingId') - 2) + "'>" + (result.getAttribute(
                            'data-pagingId') - 2) + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + (result.getAttribute(
                            'data-pagingId') - 1) + "'>" + (result.getAttribute(
                            'data-pagingId') - 1) + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + (result.getAttribute(
                            'data-pagingId')) + "'>" + result.getAttribute(
                            'data-pagingId') + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + (Number(result.getAttribute(
                            'data-pagingId')) + 1) + "'>" + (Number(result.getAttribute(
                            'data-pagingId')) + 1) + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + (Number(result.getAttribute(
                            'data-pagingId')) + 2) + "'>" + (Number(result.getAttribute(
                            'data-pagingId')) + 2) + "</span>";
                        PagingNumHtml +=
                            "<span data-pagingId='" + ((Number(PagingNow) + 5) < pageNum ? (Number(
                                PagingNow) + 5) : pageNum) +
                            "'><i id='PagingIconRightMore'  class='iconfont icon-more'></i></span>"
                        PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
                            "</span>";
                    }
                    if (PagingNow >= (pageNum - 3) && pageNum > 7) {
                        PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
                        PagingNumHtml +=
                            "<span data-pagingId='" + ((Number(PagingNow) - 5) >= 1 ? (Number(PagingNow) -
                                5) : 1) +
                            "'><i id='PagingLeftIconMore'  class='iconfont icon-more'></i></span>"
                        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 5) + "'>" + (
                            pageNum - 5) + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 4) + "'>" + (
                            pageNum - 4) + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 3) + "'>" + (
                            pageNum - 3) + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 2) + "'>" + (
                            pageNum - 2) + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + (pageNum - 1) + "'>" + (
                            pageNum - 1) + "</span>";
                        PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
                            "</span>";
                    }
                    document.getElementById('PagingNum').innerHTML = PagingNumHtml
                    if (document.getElementById('PagingIconRightMore')) {
                        document.getElementById('PagingIconRightMore').addEventListener('mouseover',
                            function () {
                                this.classList.remove("icon-more");
                                this.classList.add("icon-shuangyoujiantou-");
                            }, false)
                        document.getElementById('PagingIconRightMore').addEventListener('mouseout',
                            function () {
                                this.classList.add("icon-more");
                                this.classList.remove("icon-shuangyoujiantou-");
                            }, false)
                        document.getElementById('PagingIconRightMore').addEventListener('click',
                            function (event) {
                                event.preventDefault;
                                if (Number(PagingNow) + 5 <= pagerCount) {
                                    PagingNow += 5
                                }
                            }, false)
                    }
                    if (document.getElementById('PagingLeftIconMore')) {
                        document.getElementById('PagingLeftIconMore').addEventListener('mouseover',
                            function () {
                                this.classList.remove("icon-more");
                                this.classList.add("icon-shuangzuojiantou-");
                            }, false)
                        document.getElementById('PagingLeftIconMore').addEventListener('mouseout',
                            function () {
                                this.classList.add("icon-more");
                                this.classList.remove("icon-shuangzuojiantou-");
                            }, false)
                    }
                    document.getElementsByClassName("PagingNext")[0].setAttribute('data-pagingId', (((
                            Number(PagingNow) + 1) < pageNum ? (Number(PagingNow) + 1) :
                        pageNum)))
                    document.getElementsByClassName("PagingPrev")[0].setAttribute('data-pagingId', (((
                        Number(PagingNow) - 1) > 0 ? (Number(PagingNow) - 1) : 1)))
                    Handle()
                }
            }
        })
    }
    Handle()

    document.getElementById('PagingSizeSelect').addEventListener('click', function () {
        if (PagingFlage) {
            PagingFlage = false;
            document.getElementById('PagingSizeSelectImg').style.transform = 'rotate(-180deg)';
            document.getElementById('PagingSizeSelectImg').style.transition = '.5s';
            document.getElementById('PagingSizeSelectUl').style.display = 'block';
        } else {
            PagingFlage = true;
            document.getElementById('PagingSizeSelectImg').style.transform = '  rotate(0deg)';
            document.getElementById('PagingSizeSelectImg').style.transition = '.5s';
            document.getElementById('PagingSizeSelectUl').style.display = 'none';
        }
        var pageSizeList = Array.prototype.slice.call(document.getElementById('PagingSizeSelectUl').getElementsByTagName(
            'li'))
        pageSizeList.map(function (res, index) {
            pageSizeList[index].addEventListener('mouseover', function () {
                this.style.background = '#ddd'
                if (index != selectSize.indexOf(pageSize)) {
                    pageSizeList[selectSize.indexOf(pageSize)].style.background = 'white'
                }

            }, false)
            pageSizeList[index].addEventListener('mouseout', function () {
                this.style.background = 'white'
            }, false)
            pageSizeList[index].addEventListener('click', function (event) {
                event.preventDefault;
                PagingNow = 1;
                // object.getPage(PagingNow)
                document.getElementById('PagingNow').value = PagingNow
                pageSizeList[selectSize.indexOf(pageSize)].style.background = 'white';
                pageSizeList[selectSize.indexOf(pageSize)].style.color = 'black';
                this.style.background = '#ddd';
                this.style.color = '#409eff';
                pageSize = Number(this.getAttribute('data-num'));
                object.getPageAmount(pageSize,PagingNow)
                document.getElementById('PagingSize').innerHTML = pageSize
                pageNum = Math.ceil(totalNum / pageSize);
                PagingNumHtml = '';
                for (var i = 1; i <= pageNum; i++) {
                    if (i <= pagerCount - 1) {
                        PagingNumHtml += "<span data-pagingId = '" + i + "'>" + i +
                            "</span>";
                    }
                    if (i == pagerCount) {
                        if (pageNum == pagerCount) {
                            PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" +
                                pageNum + "</span>";
                        } else {
                            PagingNumHtml +=
                                "<span data-pagingId='" + ((Number(PagingNow) + 5) <
                                    pageNum ?
                                    (Number(PagingNow) + 5) : pageNum) +
                                "'><i id='PagingIconRightMore'  class='iconfont icon-more'></i></span>";
                            PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" +
                                pageNum + "</span>";
                        }
                    }
                }
                document.getElementById('PagingNum').innerHTML = PagingNumHtml
                if (document.getElementById('PagingIconRightMore')) {
                    document.getElementById('PagingIconRightMore').addEventListener(
                        'mouseover',
                        function () {
                            this.classList.remove("icon-more");
                            this.classList.add("icon-shuangyoujiantou-");
                        }, false)
                    document.getElementById('PagingIconRightMore').addEventListener(
                        'mouseout',
                        function () {
                            this.classList.add("icon-more");
                            this.classList.remove("icon-shuangyoujiantou-");
                        }, false)
                }
                if (document.getElementById('PagingLeftIconMore')) {
                    document.getElementById('PagingLeftIconMore').addEventListener(
                        'mouseover',
                        function () {
                            this.classList.remove("icon-more");
                            this.classList.add("icon-shuangzuojiantou-");
                        }, false)
                    document.getElementById('PagingLeftIconMore').addEventListener(
                        'mouseout',
                        function () {
                            this.classList.add("icon-more");
                            this.classList.remove("icon-shuangyoujiantou-");
                        }, false)
                }
            })
        })
        document.getElementById('PagingSizeSelectUl').addEventListener('mouseout', function (ev) {
            ev.stopPropagation()
            pageSizeList[selectSize.indexOf(pageSize)].style.background = '#ddd';
            Handle()
        }, false)
    }, false)
    if (document.getElementById('PagingIconRightMore')) {
        document.getElementById('PagingIconRightMore').addEventListener('mouseover',
            function () {
                this.classList.remove("icon-more");
                this.classList.add("icon-shuangyoujiantou-");
            }, false)
        document.getElementById('PagingIconRightMore').addEventListener('mouseout',
            function () {
                this.classList.add("icon-more");
                this.classList.remove("icon-shuangyoujiantou-");
            }, false)
    }
    if (document.getElementById('PagingLeftIconMore')) {
        document.getElementById('PagingLeftIconMore').addEventListener('mouseover',
            function () {
                this.classList.remove("icon-more");
                this.classList.add("icon-shuangzuojiantou-");
            }, false)
        document.getElementById('PagingLeftIconMore').addEventListener('mouseout',
            function () {
                this.classList.add("icon-more");
                this.classList.remove("icon-shuangyoujiantou-");
            }, false)
    }
    document.getElementById('PagingNow').onfocus = function () {
        document.onkeydown = function (event) {
            if (event.keyCode == 13) {
                document.getElementById('PagingNow').blur()
                document.onkeydown = false;


            }
        }
    }
    document.getElementById('PagingNow').onblur = function () {
        document.onkeydown = false;

        PagingNow = document.getElementById('PagingNow').value
        if (PagingNow <= pagerCount && pageNum <=
            pagerCount) {
            object.getPage(PagingNow)
            for(var i = 1; i <= pageNum; i ++){
                PagingNumHtml += "<span data-pagingId = '" + i  + "'>" + i  + "</span>";
            }
            // PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
            // PagingNumHtml += "<span data-pagingId = '" + 2 + "'>" + 2 + "</span>";
            // PagingNumHtml += "<span data-pagingId = '" + 3 + "'>" + 3 + "</span>";
            // PagingNumHtml += "<span data-pagingId = '" + 4 + "'>" + 4 + "</span>";
            // PagingNumHtml += "<span data-pagingId = '" + 5 + "'>" + 5 + "</span>";
            // PagingNumHtml += "<span data-pagingId = '" + 6 + "'>" + 6 + "</span>";
            // PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
            //     "</span>";

        }
        if (PagingNow < 5 && pageNum > pagerCount) {
            object.getPage(PagingNow)
            PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + 2 + "'>" + 2 + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + 3 + "'>" + 3 + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + 4 + "'>" + 4 + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + 5 + "'>" + 5 + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + 6 + "'>" + 6 + "</span>";
            PagingNumHtml +=
                "<span data-pagingId='" + ((Number(PagingNow) + 5) < pageNum ? (Number(
                    PagingNow) + 5) : pageNum) +
                "'><i id='PagingIconRightMore'  class='iconfont icon-more'></i></span>"
            PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
                "</span>";
        }
        if (PagingNow >= 5 && pageNum > 7 &&
            PagingNow < (pageNum - 3)) {
            object.getPage(PagingNow)
            PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
            PagingNumHtml +=
                "<span data-pagingId='" + ((Number(PagingNow) - 5) >= 1 ? (Number(PagingNow) -
                    5) : 1) +
                "'><i id='PagingLeftIconMore'  class='iconfont icon-more'></i></span>"
            PagingNumHtml += "<span data-pagingId = '" + (PagingNow - 2) + "'>" + (PagingNow - 2) +
                "</span>";
            PagingNumHtml += "<span data-pagingId = '" + (PagingNow - 1) + "'>" + (PagingNow - 1) +
                "</span>";
            PagingNumHtml += "<span data-pagingId = '" + (PagingNow) + "'>" + PagingNow + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + (Number(PagingNow) + 1) + "'>" + (Number(
                PagingNow) + 1) + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + (Number(PagingNow) + 2) + "'>" + (Number(
                PagingNow) + 2) + "</span>";
            PagingNumHtml +=
                "<span data-pagingId='" + ((Number(PagingNow) + 5) < pageNum ? (Number(
                    PagingNow) + 5) : pageNum) +
                "'><i id='PagingIconRightMore'  class='iconfont icon-more'></i></span>"
            PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
                "</span>";
        }
        if (PagingNow >= (pageNum - 3) && pageNum > 7) {
            object.getPage(PagingNow)
            PagingNumHtml += "<span data-pagingId = '" + 1 + "'>" + 1 + "</span>";
            PagingNumHtml +=
                "<span data-pagingId='" + ((Number(PagingNow) - 5) >= 1 ? (Number(PagingNow) -
                    5) : 1) +
                "'><i id='PagingLeftIconMore'  class='iconfont icon-more'></i></span>"
            PagingNumHtml += "<span data-pagingId = '" + (pageNum - 5) + "'>" + (
                pageNum - 5) + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + (pageNum - 4) + "'>" + (
                pageNum - 4) + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + (pageNum - 3) + "'>" + (
                pageNum - 3) + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + (pageNum - 2) + "'>" + (
                pageNum - 2) + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + (pageNum - 1) + "'>" + (
                pageNum - 1) + "</span>";
            PagingNumHtml += "<span data-pagingId = '" + pageNum + "'>" + pageNum +
                "</span>";
        }
        document.getElementById('PagingNum').innerHTML = PagingNumHtml;
        var PagingNum = Array.prototype.slice.call(document.getElementById('PagingDevice').getElementsByTagName(
            'span'));
        PagingNum.map(function (res) {
            if (res.getAttribute('data-pagingId') == PagingNow) {
                res.style.color = '#409eff'
            }
        })
        if (document.getElementById('PagingIconRightMore')) {
            document.getElementById('PagingIconRightMore').addEventListener('mouseover',
                function () {
                    this.classList.remove("icon-more");
                    this.classList.add("icon-shuangyoujiantou-");
                }, false)
            document.getElementById('PagingIconRightMore').addEventListener('mouseout',
                function () {
                    this.classList.add("icon-more");
                    this.classList.remove("icon-shuangyoujiantou-");
                }, false)
            document.getElementById('PagingIconRightMore').addEventListener('click',
                function (event) {
                    event.preventDefault;
                    if (Number(PagingNow) + 5 <= pagerCount) {
                        PagingNow += 5
                    }
                }, false)
        }
        if (document.getElementById('PagingLeftIconMore')) {
            document.getElementById('PagingLeftIconMore').addEventListener('mouseover',
                function () {
                    this.classList.remove("icon-more");
                    this.classList.add("icon-shuangzuojiantou-");
                }, false)
            document.getElementById('PagingLeftIconMore').addEventListener('mouseout',
                function () {
                    this.classList.add("icon-more");
                    this.classList.remove("icon-shuangzuojiantou-");
                }, false)
        }
        document.getElementsByClassName("PagingNext")[0].setAttribute('data-pagingId', (((
                Number(PagingNow) + 1) < pageNum ? (Number(PagingNow) + 1) :
            pageNum)))
        document.getElementsByClassName("PagingPrev")[0].setAttribute('data-pagingId', (((
            Number(PagingNow) - 1) > 0 ? (Number(PagingNow) - 1) : 1)))

        Handle()
    }
}