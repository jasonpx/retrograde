<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Retrograde</title>

    <link rel="stylesheet" type="text/css" href="../js/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="../js/bootstrap/css/bootstrap-theme.min.css"/>

    <script type="text/javascript" src="../js/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/d3/d3.min.js"></script>
    <script type="text/javascript" src="../js/trianglify/trianglify.js"></script>

</head>

<body>


<script>
    var t = new Trianglify({
        noiseIntensity: 0.0,
        cellpadding: 100,
        cellsize: 100,
        fillOpacity: 0.6,
        strokeOpacity: 0.0
    });
    var pattern = t.generate(window.innerWidth, window.innerHeight);
    document.body.setAttribute('style', 'background-size: cover; min-height: 100%; background-repeat: no-repeat; background-position: center center; background-image: ' + pattern.dataUrl);
</script>

</body>
</html>