<!-- login_panel.inc BEGIN -->
<div id="login_panel">
	<form action="/signin" method="post">
		<table>
			<tr>
				<td>
					Логин:
				</td>
				<td>
					<input type="text" name="login" />
				</td>
			</tr>
			<tr>
				<td>
					Пароль:
				</td>
				<td>
					<input type="password" name="password" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="Войти" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<a href="/signup">Зарегистрироваться</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<!-- login_panel.inc END -->
