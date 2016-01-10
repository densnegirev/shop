<!-- catalog_table.inc BEGIN -->
<table class="productsTab">
	<tr>
		<td class="center">Предпросмотр</td>
		<td class="center">Информация</td>
		<td class="center">Действия</td>
	</tr>
	${rows}
	<form action="/admin" method="post" enctype="multipart/form-data">
	<tr>
		<td class="preview center">

		</td>
		<td>
			<table class="catalogSubTab">
				<tr>
					<td class="right">Имя производителя</td>
					<td><input type="text" name="fabricname" /></td>
				</tr>
				<tr>
					<td class="right">Страна производителя</td>
					<td><input type="text" name="fabriccountry" /></td>
				</tr>
				<tr>
					<td class="right">Тип</td>
					<td><input type="text" name="type" /></td>
				</tr>
				<tr>
					<td class="right">Формат</td>
					<td><input type="text" name="hdformat" /></td>
				</tr>
				<tr>
					<td class="right">Разрешение</td>
					<td><input type="text" name="resolution" /></td>
				</tr>
				<tr>
					<td class="right">Модель</td>
					<td><input type="text" name="model" /></td>
				</tr>
				<tr>
					<td class="right">Диагональ</td>
					<td><input type="text" name="diagonal" /></td>
				</tr>
				<tr>
					<td class="right">Цена</td>
					<td><input type="text" name="price" /></td>
				</tr>
				<tr>
					<td class="right">Количество</td>
					<td><input type="text" name="count" /></td>
				</tr>
			</table>
		</td>
		<td class="center">
			<input type="hidden" name="itemid" value="${itemId}" />
			<input name="add" type="submit" value="Добавить" />
		</td>
	</tr>
	</form>
</table>
<!-- catalog_table.inc END -->
