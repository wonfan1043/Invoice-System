$(function () {
    // 初期のセクションを非表示にする
    const signUpArea = $(".signUpArea");
    const loginArea = $(".loginArea");
    const forgotArea = $(".forgotArea");
    signUpArea.hide();
    loginArea.hide();
    forgotArea.hide();

    // ボタンのクリックを処理する
    $(".goSignUp").click(function () {
        loginArea.fadeOut(100); // ログインエリアを100ミリ秒かけてフェードアウトする
        forgotArea.fadeOut(100); // パスワードリセットエリアを100ミリ秒かけてフェードアウトする
        setTimeout(() => {
            signUpArea.fadeIn(100); // サインアップエリアを100ミリ秒かけてフェードインする（遅延実行）
        }, 100);
    });

    $(".goLogin").click(function () {
        signUpArea.fadeOut(100); // サインアップエリアを100ミリ秒かけてフェードアウトする
        forgotArea.fadeOut(100); // パスワードリセットエリアを100ミリ秒かけてフェードアウトする
        setTimeout(() => {
            loginArea.fadeIn(100); // ログインエリアを100ミリ秒かけてフェードインする（遅延実行）
        }, 100);
    });

    $(".goForgot").click(function () {
        signUpArea.fadeOut(100); // サインアップエリアを100ミリ秒かけてフェードアウトする
        loginArea.fadeOut(100); // ログインエリアを100ミリ秒かけてフェードアウトする
        setTimeout(() => {
            forgotArea.fadeIn(100); // パスワードリセットエリアを100ミリ秒かけてフェードインする（遅延実行）
        }, 100);
    });

    $(".cancel").click(function () {
        signUpArea.fadeOut(100); // サインアップエリアを100ミリ秒かけてフェードアウトする
        loginArea.fadeOut(100); // ログインエリアを100ミリ秒かけてフェードアウトする
        forgotArea.fadeOut(100); // パスワードリセットエリアを100ミリ秒かけてフェードアウトする
    });

    $(".goBack").click(function () {
        window.history.back(); // ブラウザの履歴を一つ戻る
    });
});

$(function() {
    $("#msg-box-edit,#msg-box-add").hide();

    // show-add-item要素のクリックイベントハンドラ
    $("#show-add-item").click(function() {
        $("#msg-box-add").fadeIn(100); // msg-box-add要素を100ミリ秒かけてフェードインする
    });

    // キャンセルボタンのクリックイベントハンドラ
    $(".cancel").click(function() {
        $("#msg-box-add, #msg-box-edit").fadeOut(100); // msg-box-addとmsg-box-edit要素を100ミリ秒かけてフェードアウトする
    });

    // edit-item要素のクリックイベントハンドラ
    $(".edit-item").click(function() {
        $("#innerCategoryId").val(this.id); // innerSubCategoryIdクラスを持つ要素の値をこの要素のidに設定する
        $("#msg-box-edit").fadeIn(100); // msg-box-edit要素を100ミリ秒かけてフェードインする
    });
});

$(document).ready(function() {
    // 初期状態ではボタン、チェックボックス、<a> 要素を非表示にする
    $(".cancelBtn, .removedBtn, input[type='checkbox']").hide();

    // bulkRemoveBtnにクリックイベントハンドラを追加する
    $(".bulkRemoveBtn").click(function() {
        // ボタンとチェックボックスを表示し、<a> 要素を非表示にする
        $(".cancelBtn, .removedBtn, input[type='checkbox']").show();
        $(".removingHide, .bulkRemoveBtn").hide();
    });

    // cancelBtnにクリックイベントハンドラを追加する
    $(".cancelBtn").click(function() {
        // ボタンとチェックボックスを非表示にし、<a> 要素を表示する
        $(".cancelBtn, .removedBtn, input[type='checkbox']").hide();
        $(".removingHide,.bulkRemoveBtn").show();
    });
});