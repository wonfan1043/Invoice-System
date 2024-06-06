$(document).ready(function(){

    // 検索条件：年のoptionを作成する
    // 2020をベースにして、毎年新しいoptionを追加する
    let baseYear = 2020;
    for(let i = baseYear; i < (new Date().getFullYear() + 1); i++){
        let yearOption = $(`<option value="${i}">${i}</option>`);
        $("#year").append(yearOption);
    }
    
    // 検索条件の年と月のデフォルト設定
    let year = new Date().getFullYear();
    let month = new Date().getMonth() + 1;
    $("#year").val(year);
    $("#month").val(month);

    $(document).on('mouseenter', '.invoice', function(){
        $(this).css({
            "background": "rgb(0, 59, 104, 0.23)",
            "box-shadow": "0 0 0px 6px rgb(0, 59, 104, 0.23)"
        });
    })
    
    $(document).on('mouseleave', '.invoice', function(){
        $(this).css({
            "background": "none",
            "box-shadow": "none"
        });
    })

    // 新規ボタンのクリックイベント処理
    $(".addBtn").click(function(){
        // 新規作成ページへ飛ばす
        window.location.href = "/manager/invoice/create_invoice";
    })
    
    // 検索ボタンのクリックイベント処理
    $(".searchBtn").click(function(){
        // 日付タイプを取得する
        let dateTypeData = $("#dateType").val();
        // 年を取得する
        let yearData = $("#year").val();
        // 月を取得する
        let monthData = $("#month").val();
        // 協力会社名を取得する
        let corpData;
        if($("#corp").val() == "null"){
            corpData = null;
        } else {
            corpData = $("#corp").val();
        }
        // ページ番号を1にする
        let pageNumData = 1;
        // 検索条件のオブジェクトを宣言する
        let req = {
            pageNum: pageNumData,
            dateType: dateTypeData,
            year: yearData,
            month: monthData,
            corpName: corpData
        }
        // 請求書を検索する
        $.ajax({
            url: '/manager/invoice/search_invoice',
            type: 'POST',
            data: JSON.stringify(req),
            contentType: "application/json",
            success: function (data) {
                // 見つからない場合は画面をクリアしてダイヤログを表示する
                if(!data.includes('<div class="invoice">')){
                    $("#list").empty();
                    $(".previousPageBtn").hide();
                    $(".nextPageBtn").hide();
                    swal({
                        title: "残念です！",
                        text: "請求書見つかりませんでした。",
                    });
                } else {
                    // 成功の場合、データを画面に表示する
                    $("#list").replaceWith(data);
                    // 前/次ページボタンの活性化を設定する
                    showBtn();
                }
            }
        })
    })
    
    // 詳細ボタンのクリックイベント処理
    $(document).on('click', '.eachInvoice .viewBtn', function(){
        // 選択された請求書の番号を取得する
        let invoiceIdData = $(this).data("invoice-to-view");
        // 請求書詳細を取得して、詳細ページへ遷移する
        window.location.href = `/manager/invoice/invoice_view?invoiceNo=${invoiceIdData}`;
    })
    
    // 削除ボタンのクリックイベント処理
    $(document).on('click', '.eachInvoice .deleteBtn', function(){
        // 選択された請求書の番号を取得する
        let invoiceIdData = $(this).data("invoice-to-delete");
        // 再確認アラート
        swal({
            title: "よろしいですか？",
            text: `請求書「${invoiceIdData}」を削除してよろしいでしょうか？`,
            buttons: {
                confirm: {
                    text: "はい",
                    visible: true
                },
                cancel: {
                    text: "いいえ",
                    visible: true
                }
            }
        }).then((res) => {
            // 削除を確認した場合
            if(res == true){
                // 主旨モデルを宣言する
                let invoice = {
                    invoiceNo: invoiceIdData,
                    updater: 'admin',
                };
                // 請求書を削除する
                $.ajax({
                    url: '/manager/invoice/delete_invoice',
                    type: 'POST',
                    data: JSON.stringify(invoice),
                    contentType: "application/json",
                    success: function(data){
                        // 成功の場合、メッセージを表示する
                        if (data.code === 1006) {
                            // 成功のメッセージ
                            swal({
                                title: "完了です！",
                                text: "請求書は削除されました。",
                            });
                            // 請求書リストを検索して画面を更新する
                            $(".searchBtn").trigger('click');
                        } else {
                            // 失敗の場合、エラーメッセージを表示する
                            swal({
                                title: "残念です！",
                                text: `${data.message}!`,
                            })
                        }
                    }
                })
            }
        })
    })
    
    // 次ページボタンのクリックイベント処理
    $(".nextPageBtn").click(function(){
        // 現在のページが最後のページの場合、検索しない
        let currentPageNum = $('#currentPageNum').val();
        let lastPageNum = $('#lastPageNum').val();
        if(currentPageNum == lastPageNum){
            return swal({
                title: "もうないです！",
                text: "現在のページは最後のページです。",
            });
        }
        // 検索条件の日付タイプ、年、月と協力会社名を取得する
        let dateTypeData = $("#dateType").val();
        let yearData = $("#year").val();
        let monthData = $("#month").val();
        let corpData;
        // 協力会社選択されていない場合はヌルにする
        if($("#corp").val() == "null"){
            corpData = null;
        } else {
            corpData = $("#corp").val();
        }
        // 検索ページ番号は現在のページページ番号に1を加える
        let pageNumData = Number($("#currentPageNum").val()) + 1;
        // 検索条件のオブジェクトを宣言する
        let req = {
            pageNum: pageNumData,
            dateType: dateTypeData,
            year: yearData,
            month: monthData,
            corpName: corpData
        }
        // 請求書を検索する
        $.ajax({
            url: '/manager/invoice/search_invoice',
            type: 'POST',
            data: JSON.stringify(req),
            contentType: "application/json",
            // データを表示して前/次ページボタンの活性化を設定する
            success: function (data) {
                $("#list").replaceWith(data);
                showBtn();
            }
        })
    })
    
    // 前ページボタンのクリックイベント処理
    $(".previousPageBtn").click(function(){
        // 日付タイプを取得する
        let dateTypeData = $("#dateType").val();
        // 年を取得する
        let yearData = $("#year").val();
        // 月を取得する
        let monthData = $("#month").val();
        // 協力会社名を取得する
        let corpData;
        // 選択されていない場合はヌルにする
        if($("#corp").val() == "null"){
            corpData = null;
        } else {
            corpData = $("#corp").val();
        }
        // 検索ページ番号は現在のページページ番号に1を減らす
        let pageNumData = Number($("#currentPageNum").val()) - 1;
        // 検索条件のオブジェクトを宣言する
        let req = {
            pageNum: pageNumData,
            dateType: dateTypeData,
            year: yearData,
            month: monthData,
            corpName: corpData
        }
        // 請求書を検索する
        $.ajax({
            url: '/manager/invoice/search_invoice',
            type: 'POST',
            data: JSON.stringify(req),
            contentType: "application/json",
            // データを表示して前/次ページボタンの活性化を設定する
            success: function (data) {
                $("#list").replaceWith(data);
                showBtn();
            }
        })
    })

    // 前ページボタンと次ページボタンの活性化を設定するメソッド
    function showBtn(){
        // ページ番号 = 1 の場合は前ページ番号を非活性化にする
        if($("#currentPageNum").val() == 1){
            $(".previousPageBtn").hide();
        } else {
            $(".previousPageBtn").show();
        }

        // 画面に主旨データがない場合は次ページボタンを非活性化にする
        if($(".invoice").length == 0){
            $(".nextPageBtn").hide();
        } else {
            $(".nextPageBtn").show();
        }
    }

    // 画面初期処理：前ページボタンと次ページボタンの活性化を設定する
    showBtn();

})