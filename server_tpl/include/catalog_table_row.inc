<!-- catalog_table_row.inc BEGIN -->
<form action="/admin_catalog" method="post">
<tr>
	<td class="preview"><img src="${image}" /></td>
	<td>
		<table class="catalogSubTab">
			<tr>
				<td class="right">Имя производителя</td>
				<!-- <td><input type="text" name="fabricname" value="${fabricName}" /></td> -->
				<td>${fabricName}</td>
			</tr>
			<tr>
				<td class="right">Страна производителя</td>
				<!-- <td><input type="text" name="fabriccountry" value="${fabricCountry}" /></td> -->
				<td>${fabricCountry}</td>
			</tr>
			<tr>
				<td class="right">Тип</td>
				<!-- <td><input type="text" name="type" value="${type}" /></td> -->
				<td>${type}</td>
			</tr>
			<tr>
				<td class="right">Формат</td>
				<!-- <td><input type="text" name="hdformat" value="${hdFormat}" /></td> -->
				<td>${hdFormat}</td>
			</tr>
			<tr>
				<td class="right">Разрешение</td>
				<!-- <td><input type="text" name="resolution" value="${resolution}" /></td> -->
				<td>${resolution}</td>
			</tr>
			<tr>
				<td class="right">Модель</td>
				<!-- <td><input type="text" name="model" value="${model}" /></td> -->
				<td>${model}</td>
			</tr>
			<tr>
				<td class="right">Диагональ</td>
				<!-- <td><input type="text" name="diagonal" value="${diagonal}" /></td> -->
				<td>${diagonal}</td>
			</tr>
			<tr>
				<td class="right">Цена</td>
				<!-- <td><input type="text" name="price" value="${price}" /></td> -->
				<td>${price}</td>
			</tr>
			<tr>
				<td class="right">Количество</td>
				<!-- <td><input type="text" name="count" value="${count}" /></td> -->
				<td>${count}</td>
			</tr>
		</table>
	</td>
	<td class="center">
		<input type="hidden" name="itemid" value="${itemId}" />
		<!-- <input name="edit" type="submit" value="Изменить" /> -->
		<input name="delete" type="submit" value="Удалить" />
	</td>
</tr>
</form>
<!-- catalog_table_row.inc END -->
