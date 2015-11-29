<!-- products_table_row.inc BEGIN -->
<tr>
	<td class="preview"><img src="${image}" /></td>
	<td>
		<ul>
			<li>Изготовитель: ${fabricName}</li>
			<li>Страна: ${fabricCountry}</li>
			<li>Тип: ${type}</li>
			<li>Формат: ${format}</li>
			<li>Разрешение: ${resolution}</li>
			<li>Модель: ${model}</li>
			<li>Диагональ: ${diagonal}</li>
			<li>Цена: ${price}</li>
		</ul>
		<br />
		<form action="/trash" method="post">
			<input type="hidden" name="action" value="add" />
			<input type="hidden" name="itemid" value="${itemId}" />

			<table class="trashTab">
				<tr class="trashTab">
					<td class="count">
						<div class="${countColor}">
                        	${count}
                        </div>
					</td>
					<td class="amount">
						<input type="text" name="amount" value="1" />
					</td>
					<td class="toTrash">
						<input type="submit" value="В корзину" />
					</td>
				</tr>
			</table>
		</form>
	</td>
</tr>
<!-- products_table_row.inc END -->
