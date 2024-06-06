$(document).ready(function(){

    // 取消ボタンのクリックイベント処理
    $(".cancelBtn").click(function(){
        // 再確認アラート
        swal({
            title: "よろしいですか？",
            text: "編集した内容は保存されませんが、よろしいでしょうか？",
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
            if(res == true){
                // 主旨リストページへ戻る
                window.location.href = "/manager/invoice/topic_list";
            }
        })
    })
    
    // 保存ボタンのクリックイベント処理
    $(".saveBtn").click(function(){
        // 主旨名を取得する
        const topicInput = $(".topic").val();
        // 主旨IDを取得する
        const topicIdInput = $(".topicId").val();
        // コメントを取得する
        const commentInput = $(".comment").val();
        // 入力チェック
        // 主旨が入力されたかチェックする
        if(topicInput.length == 0){
            return swal({
                title: "まだまだ！",
                text: "請求書主旨を入力してください。",
            });
        }
        // 主旨が文字のみであるかチェックする
        let topicPattern = /^[\p{L}\d]|[\p{L}\d]{1}[\p{L}\d ]+$/u;
        if(!topicPattern.test(topicInput)){
            return swal({
                title: "お願い！",
                text: "主旨は文字のみ入力してください。",
            });
        }
        // 主旨IDが入力されたかチェックする
        if(topicIdInput.length == 0){
            return swal({
                title: "まだまだ！",
                text: "請求書主旨IDを入力してください。",
            });
        }
        // 主旨IDが英語のみであるかチェックする
        let topicIdPattern = /^[A-Za-z]|[A-Za-z][A-Za-z ]+$/;
        if(!topicIdPattern.test(topicIdInput)){
            return swal({
                title: "お願い！",
                text: "主旨IDは英語のみ入力してください。",
            });
        }
        // コメントが入力された場合、文字のみであるかチェックする
        if(commentInput.length > 0){
            let commentPattern = /^[\p{L}\d]|[\p{L}\d]{1}[\p{L}\d ]+$/u;
            if(!commentPattern.test(commentInput)){
                return swal({
                    title: "お願い！",
                    text: "コメントは文字のみ入力してください。",
                });
            }
        }
        // 主旨モデルを宣言する
        let topic = {
            topicId: topicIdInput,
            topicName: topicInput,
            comment: commentInput,
            updater: "admin",
        }
        // 主旨を保存する
        $.ajax({
            url: '/manager/invoice/update_topic',
            type: 'POST',
            data: JSON.stringify(topic),
            contentType: "application/json",
            success: function(data) {
                // 成功の場合、メッセージを表示して、主旨リストページへ戻る
                if(data.code == 1008){
                    swal({
                        title: "ナイス！",
                        text: "主旨編集しました！",
                    })
                    .then((res) => {
                        if(res == true){
                            window.location.href = '/manager/invoice/topic_list';
                        }
                    })
                } else {
                    // エラーの場合はメッセージを表示する
                    swal({
                        title: "残念です！",
                        text: `${data.message}!`,
                    })
                }
            }
        })
    })

})