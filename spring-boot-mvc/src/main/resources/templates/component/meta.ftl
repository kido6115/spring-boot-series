<#ftl output_format="HTML" />
<#global htmlEscape=true />
<#setting url_escaping_charset="UTF-8" />
<#import "/spring.ftl" as spring />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="_csrf_parameter" content="${(_csrf.parameterName)!}"/>
<meta name="_csrf_header" content="${(_csrf.headerName)!}"/>
<meta name="_csrf" content="${(_csrf.token)!}"/>
<link rel="shortcut icon" href="<@spring.url "/img/favicon.ico" />"/>
<link rel="bookmark" href="<@spring.url "/img/favicon.ico" />"/>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
      integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
<title>Sungyeh Tech Note</title>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
        crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<link rel="stylesheet" href="/css/monokai.css">
<script src="/js/rainbow-custom.min.js"></script>
<link rel="stylesheet" href="/css/style.css">
<style>
    @import url(https://fonts.googleapis.com/earlyaccess/cwtexyen.css);

    body {
        font-family: 'cwlexYen', sans-serif;
    !important;
    }

    .tag.close {
        float: unset;
        font-size: unset;
        font-weight: unset;
    }
</style>
<script>
    $(document).ajaxSend(function (event, jqXHR, ajaxOptions) {
        jqXHR.setRequestHeader("${(_csrf.headerName)!}", "${(_csrf.token)!}");
    });
</script>
