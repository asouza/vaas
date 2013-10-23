<html>
<body>

	<h1>Welcome to the VAAS Test (using JAAS)</h1>
	
	<h2>Login</h2>
	<p>${message}</p>
	
	<form method="post" action="login">
			<label for="login">Login:</label>
			<input type="text" name="login" size="15" id="login" />
			<br />
			<label for="senha">Password:</label>
			<input type="password" size="15" name="password" id="password" />
			<br />
			<input type="submit" value="Login" />
	</form>
</body>
</html>