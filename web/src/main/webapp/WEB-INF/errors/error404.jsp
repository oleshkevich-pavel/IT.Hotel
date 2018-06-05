<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/css/materialize.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/js/materialize.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/custom.css">

<html>
<title>Error Page</title>
<body>
	<div class="row">
		<div class="col s12">
			<h5 class="center-align">Ошибка 404. Страница не найдена!</h5>
		</div>
	</div>
	<div class="row">
		<div class="col s12">
			<p>Что обозначают цифры в ошибке 404?</p>
			<p>Не всем известно, что первая цифра говорит об ошибке
				программы-клиента (браузера). Предполагается, что URL-адрес не
				существует или набран неверно. Ноль указывает на проблемы
				синтаксиса. Последнюю цифру относят к разновидности ошибок 40х,
				включающей, кроме статуса 404 not found, 401 unauthorized и 400 bad
				request.</p>
		</div>
	</div>
	<div class="row">
		<div align="center" class="col s12">
			<img alt="404 page not found"
				src="https://5bucks.ru/wp-content/uploads/2017/06/404.png">
		</div>
	</div>
	<div class="row">
		<div class="col s12">
			<p>Есть забавный миф об ошибке 404, согласно которому, в научном
				центре, участвующем в разработке World Wide Web была комната с таким
				же номером, находившаяся на четвертом этаже. В определенный момент
				объем информации, которая обрабатывалась, превысил все допустимые
				нормы, несколько ученых не смогли обратиться одновременно к одному
				документу. Вот тогда к разработчикам и пришла мысль о том, чтобы
				сделать сообщение об ошибке, оповещающей о том, что файл не найден.</p>
			<p>Сами ученые из центра CERN это опровергают. Они утверждают,
				что такой комнаты не было. Стоит заметить, что невозможно ни разу не
				встретить error 404 not found. Есть еще одна причина появления такой
				ошибки. В России довольно распространенным является
				Интернет-соединение при помощи dial-up (телефонная связь). При
				плохой связи с провайдером появляется ошибка 404.</p>
		</div>
	</div>
</body>
</html>