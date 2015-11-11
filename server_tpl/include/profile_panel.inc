<!-- profile_panel.inc BEGIN -->
<form action="/profile" method="post">
	<table>
		<tr>
			<td>
				Пароль:
			</td>
			<td>
				<input type="password" value="${password}" name="password" />
			</td>
		</tr>
		<tr>
			<td>
				Пароль еще раз:
			</td>
			<td>
				<input type="password" value="${password_again}" name="password_again" />
			</td>
		</tr>
		<tr>
			<td>
				Фамилия:
			</td>
			<td>
				<input type="text" value="${familiya}" name="familiya" />
			</td>
		</tr>
		<tr>
			<td>
				Имя:
			</td>
			<td>
				<input type="text" value="${imya}" name="imya" />
			</td>
		</tr>
		<tr>
			<td>
				Отчество:
			</td>
			<td>
				<input type="text" value="${otchestvo}" name="otchestvo" />
			</td>
		</tr>
		<tr>
			<td>
				Почта:
			</td>
			<td>
				<input type="text" value="${email}" name="email" />
			</td>
		</tr>
		<tr>
			<td>
				Адрес:
			</td>
			<td>
				<input type="text" value="${address}" name="address" />
			</td>
		</tr>
		<tr>
			<td>
				Телефон:
			</td>
			<td>
				<input type="text" value="${phone}" name="phone" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" name="submit" value="Обновить" />
			</td>
		</tr>
	</table>
</form>
<!-- profile_panel.inc END -->
