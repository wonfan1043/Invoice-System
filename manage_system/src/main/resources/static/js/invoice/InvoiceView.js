$(document).ready(function(){

    // よく使う要素を取得しておく
    const taxRate = $('#tax');

    // 作成日を設定する
    // 要素を取得する
    const createDate = $('#createDate');
    // 作成日付を配列にする
    let createDateArr = createDate.text().split('/');
    // 画面に表示する
    createDate.text(createDateArr[0] + '　年　／　' + createDateArr[1] + '　月　／　' + createDateArr[2] + '　日');

    // 消費税と合計（税込）を設定する
    // 税率を取得する
    let tax = taxRate.text();
    // 画面に表示する
    taxRate.text(String(Number(tax)*100) + '%');
    // 合計（税込）の要素を取得する
    const sum = $("#sum");
    // 各商品の金額を取得する
    let eachSum = $(".eachSum");
    // 商品が1つの場合
    if(eachSum.length == 1){
        sum.text(parseInt(eachSum.text() * (1 + parseFloat(tax))) + "円");
    } else {
        // 複数の場合、addUpを宣言して各商品の金額を合計する
        let addUp = 0;
        for(let i = 0; i < eachSum.length; i++){
            addUp += parseInt(eachSum[i].innerText);
        }
        // 合計（税込）を表示する
        sum.text(parseInt(addUp * (1 + parseFloat(tax))) + "円");
    }

    // 決済期限を設定する
    // 決済期限の要素を取得する
    const dueDate = $('#dueDate');
    // 決済日付を配列にする
    let dueDateArr = String(dueDate.text()).split('-');
    // 画面に表示する
    dueDate.text(dueDateArr[0] + '　年　／　' + dueDateArr[1] + '　月　／　' + dueDateArr[2] + '　日');

    $(document).on('click', '.backBtn', function() {
        window.location.href = '/manager/invoice/invoice_list';
    })

    // 編集ボタンのクリックイベント処理
    $(document).on('click', '.editBtn', function() {
        // 全ての情報をlocalStorageに保存する
        localStorage.setItem("invoiceNo", $(".invoiceNo").text());
        localStorage.setItem("createDate", $("#createDate").text());
        localStorage.setItem("corpName", $(".corpName").text());
        localStorage.setItem("topicName", $(".topicName").text());
        localStorage.setItem("receiver", $(".receiver").text());
        localStorage.setItem("county", $(".county").text());
        localStorage.setItem("postcode", $(".postcode").text());
        localStorage.setItem("town", $(".town").text());
        localStorage.setItem("address", $(".addressSend").text());
        // 発送先ビル名に内容がある場合のみ保存する
        if($("#building").length > 0){
            localStorage.setItem("building", $("#building").text());
        }
        localStorage.setItem("bankName", $(".bank").text());
        localStorage.setItem("bankAddress", $(".bankAddress").text());
        localStorage.setItem("swiftCodeData", $(".swiftCode").text());
        localStorage.setItem("holder", $(".holder").text());
        localStorage.setItem("receiveAddress", $(".receiveAddress").text());
        localStorage.setItem("account", $(".account").text());
        // 全ての商品名を取得する
        let itemName = $(".itemName");
        // 要素が1つのみの場合は直接保存する
        if(itemName.length == 1){
            localStorage.setItem("itemName", itemName.text())
        } else {
            // 複数の場合は配列に入れて、Stringにして保存する
            let itemNameData = [];
            for(let i = 0; i < itemName.length; i++){
                itemNameData.push(itemName[i].innerText);
            }
            localStorage.setItem("itemName", itemNameData.toString());
        }
        // 全ての数字を取得する
        let quantity = $(".quantity");
        // 要素が1つのみの場合は直接保存する
        if(quantity.length == 1){
            localStorage.setItem("quantity", quantity.text())
        } else {
            // 複数の場合は配列に入れて、Stringにして保存する
            let quantityData = [];
            for(let i = 0; i < quantity.length; i++){
                quantityData.push(quantity[i].innerText);
            }
            localStorage.setItem("quantity", quantityData.toString());
        }
        // 全ての単価を取得する
        let unitPrice = $(".unitPrice");
        // 要素が1つのみの場合は直接保存する
        if(unitPrice.length == 1){
            localStorage.setItem("unitPrice", unitPrice.text());
        } else {
            // 複数の場合は配列に入れて、Stringにして保存する
            let unitPriceData = [];
            for(let i = 0; i < unitPrice.length; i++){
                unitPriceData.push(unitPrice[i].innerText);
            }
            localStorage.setItem("unitPrice", unitPriceData.toString());
        }
        localStorage.setItem("tax", $("#tax").text());
        localStorage.setItem("paymentDue", $("#dueDate").text());
        // メモに内容がある場合のみ保存する
        if($("#memo").length > 0){
            localStorage.setItem("memo", $("#memo").text());
        }
        // 請求書編集ページに飛ばす
        window.location.href = "/manager/invoice/edit_invoice";
    })

    // PDF出力ボタンのクリックイベント処理
    $(document).on('click', '.pdfBtn', function() {
        // ファイル名を請求書番号にするため、ページタイトルを変更する
        let createDate = createDateArr[0] + '／' + createDateArr[1] + '／' + createDateArr[2];
        let title = ($(".invoiceNo").text() + "_" + $(".corpName").text() + "_" + createDate);
        $("title").text(title);

        // bodyの内容を出力内容に置換する
        // (1) 元内容を取得する
        let oldStr = window.document.body.innerHTML;
        // (2) startマーク、17バイト
        let start = "<!--startprint-->";
        // (3) endマーク
        let end = "<!--endprint-->";
        // (4) startマークからendマークまでの内容を取得する
        let newStr = oldStr.substring(oldStr.indexOf(start) + 17);
        newStr = newStr.substring(0, newStr.indexOf(end));
        // (5) bodyの内容を出力内容に置換する
        document.body.innerHTML = newStr;
        // (6) 印刷ポップアップを呼出
        window.print();
        // (7) bodyの内容を復元する
        document.body.innerHTML = oldStr;

        // ページタイトルを復元する
        $("title").text("請求書明細プレビュー");
    })

})