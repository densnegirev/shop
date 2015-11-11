<!-- signup_panel.inc BEGIN -->
<form action="/signup" method="post">
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
			<td>
				Пароль еще раз:
			</td>
			<td>
				<input type="password" name="password_again" />
			</td>
		</tr>
		<tr>
			<td>
				Фамилия:
			</td>
			<td>
				<input type="text" name="familiya" />
			</td>
		</tr>
		<tr>
			<td>
				Имя:
			</td>
			<td>
				<input type="text" name="imya" />
			</td>
		</tr>
		<tr>
			<td>
				Отчество:
			</td>
			<td>
				<input type="text" name="otchestvo" />
			</td>
		</tr>
		<tr>
			<td>
				Почта:
			</td>
			<td>
				<input type="text" name="email" />
			</td>
		</tr>
		<tr>
			<td>
				Адрес:
			</td>
			<td>
				<textarea name="address" cols="40" rows="5"></textarea>
			</td>
		</tr>
		<tr>
			<td>
				Телефон:
			</td>
			<td>
				<input type="text" name="phone" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" name="submit" value="Зарегистрироваться" />
			</td>
		</tr>
	</table>
</form>
<div id="errors">${errors}</div>
<!-- signup_panel.inc END -->
