<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
    </style>
    <link href="./css/pray.css" rel="stylesheet">
</head>
<body>
<div class="dingwei">

    <div class="content">
        <div class="fo_tu2">
            <img src="./imgs/fo_bg_tl.jpg" width="122" height="309">
            <img src="./imgs/fo_gd_l.gif" width="37" height="309">
            <img id="fo_fx" src="./imgs/fo_fx_ysf.jpg" width="248" height="309">
            <img src="./imgs/fo_gd_r.gif" width="37" height="309">
            <img src="./imgs/fo_bg_tr.jpg" width="122" height="309">
            <img src="./imgs/fo_bg_bl.jpg" width="223" height="166">
            <img id="fo_gp" src="./imgs/fo_gp_fruit.gif" width="120" height="166">
            <img src="./imgs/fo_bg_br.jpg" width="223" height="166">
            <div class="fo_gp2">
                <button type="button" name="btn_fo" onclick="javascript:provideFo('water')" class="fo_water2 fo_water">供水</button>
                <button type="button" name="btn_fo" onclick="javascript:provideFo('incen')" class="fo_xiang2 fo_xiang">供香</button>
                <button type="button" name="btn_fo" onclick="javascript:provideFo('glass')" class="fo_flower2 fo_flower">供花</button>
                <button type="button" name="btn_fo" onclick="javascript:provideFo('friut')" class="fo_fruit2 fo_none">供果</button></div>
        </div>
    </div>
</div>
<script>
    function provideFo(arg){
        alert(arg);
    }
</script>
</body>
</html>