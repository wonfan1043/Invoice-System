$(document).ready(function(){

    // 検索条件：年のoptionを作成する
    // 2020をベースにして、毎年新しいoptionを追加する
    let baseYear = 2020;
    for(let i = baseYear; i < (new Date().getFullYear() + 1); i++){
        let yearOption = $(`<option value="${i}">${i}</option>`);
        $("#year").append(yearOption);
    }

    // 検索条件の年と月のデフォルト設定
    // (1)現時点の年と月を取得する
    let year = new Date().getFullYear();
    let month = new Date().getMonth() + 1;
    // (2)検索条件の年と月を設定する
    $('#year').val(year);
    $('#month').val(month);
    
    $(document).on('mouseenter', '.topic', function(){
        $(this).css({
            "background": "rgb(0, 59, 104, 0.23)",
            "box-shadow": "0 0 0px 6px rgb(0, 59, 104, 0.23)"
        });
    })
    
    $(document).on('mouseleave', '.topic', function(){
        $(this).css({
            "background": "none",
            "box-shadow": "none"
        });
    })

    $(".topArea").on('click', '.addBtnForm, addBtn, addText', function(){
        window.location.href = "/manager/invoice/create_topic";
    })

    // 検索ボタンのクリックイベント処理
    $(".searchBtn").click(function(){
        // ページ番号を1にする
        let pageNumData = 1;
        // 検索条件の年、月とキーワードを取得する
        let yearData = $("#year").val();
        let monthData = $("#month").val();
        let keywordData = $("#keyword").val();
        // 検索オブジェクトを定義する
        let searchTopicReq = {
            pageNum: pageNumData,
            year: yearData,
            month: monthData,
            keyword: keywordData
        }
        // 主旨を検索する
        $.ajax({
            url: "/manager/invoice/search_topic",
            type: 'POST',
            data: JSON.stringify(searchTopicReq),
            contentType: 'application/json',
            success: function (data) {
                // 主旨見つからない場合は画面をクリアしてダイヤログを表示する
                if(!data.includes('<div class="topic" id="topic">')){
                    $("#list").empty();
                    $(".previousPageBtn").hide();
                    $(".nextPageBtn").hide();
                    swal({
                        title: "残念です！",
                        text: "主旨見つかりませんでした。",
                    });
                } else {
                    // 主旨見つかった場合はデータを表示する
                    $("#list").replaceWith(data);
                    // 前/次ページボタンの活性化を設定する
                    showBtn();
                }
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
        // 検索条件の年、月とキーワードを取得する
        let yearData = $("#year").val();
        let monthData = $("#month").val();
        let keywordData = $("#keyword").val();
        // 検索ページ番号は現在のページページ番号に1を加える
        let pageNumData = currentPageNum + 1;
        // 検索オブジェクトを定義する
        let searchTopicReq = {
            pageNum: pageNumData,
            year: yearData,
            month: monthData,
            keyword: keywordData
        }
        // 主旨を検索する
        $.ajax({
            url: "/manager/invoice/search_topic",
            type: 'POST',
            data: JSON.stringify(searchTopicReq),
            contentType: 'application/json',
            success: function (data) {
                // データを表示して前/次ページボタンの活性化を設定する
                $("#list").replaceWith(data);
                showBtn();
            }
        })
    })

    // 前ページボタンのクリックイベント処理
    $(".previousPageBtn").click(function(){
        // 検索条件の年、月とキーワードを取得する
        let yearData = $("#year").val();
        let monthData = $("#month").val();
        let keywordData = $("#keyword").val();
        // 検索ページ番号は現在のページページ番号に1を減らす
        let pageNumData = currentPageNum - 1;
        // 検索オブジェクトを定義する
        let searchTopicReq = {
            pageNum: pageNumData,
            year: yearData,
            month: monthData,
            keyword: keywordData
        }
        // 主旨を検索する
        $.ajax({
            url: "/manager/invoice/search_topic",
            type: 'POST',
            data: JSON.stringify(searchTopicReq),
            contentType: 'application/json',
            // 検索結果を画面に表示する
            success: function (data) {
                // データを表示して前/次ページボタンの活性化を設定する
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
        if($(".topic").length == 0){
            $(".nextPageBtn").hide();
        } else {
            $(".nextPageBtn").show();
        }
    }

    // 画面初期処理：前ページボタンと次ページボタンの活性化を設定する
    showBtn();

    // 編集ボタンのクリックイベント処理
    $(document).on("click", ".eachTopic .editBtn", function(){
        // 主旨IDを取得する
        let topicIdData = $(this).data("topic-to-edit");
        // 主旨詳細を取得して、詳細ページへ遷移する
        window.location.href = `/manager/invoice/edit_topic?topicId=${topicIdData}`;
    })

    //削除ボタンのクリックイベント処理
    $(document).on("click", ".eachTopic .deleteBtn", function(){
        // 主旨IDと主旨名を取得
        const topicToDeleteData = $(this).data("topic-to-delete");
        let topicIdData = topicToDeleteData.split('|')[0];
        let topicNameData = topicToDeleteData.split('|')[1];
        // 再確認アラート
        swal({
            title: "よろしいですか？",
            text: `主旨「${topicNameData}」を削除してよろしいでしょうか？`,
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
                let topic = {
                    topicId: topicIdData,
                    topicName: topicNameData,
                    updater: 'admin'
                };
                // 主旨を削除する
                $.ajax({
                    url: "/manager/invoice/delete_topic",
                    type: 'POST',
                    data: JSON.stringify(topic),
                    contentType: "application/json",
                    success: function(data){
                        // 成功の場合、メッセージを表示して、再度主旨リストを検索して画面を更新する
                        if (data.code === 1006) {
                            // 成功のメッセージ
                            swal({
                                title: "完了です！",
                                text: "主旨は削除されました。",
                            });
                            // 主旨リストを検索して画面を更新する
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
    });
})