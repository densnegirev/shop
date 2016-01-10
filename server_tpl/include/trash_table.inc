<!-- trash_table.inc BEGIN -->
<table class="productsTab">
	<tr>
		<td class="preview_small center">Предпросмотр</td>
		<td class="center">Модель</td>
		<td class="center">Количество</td>
		<td class="center">Цена</td>
		<td class="center">Действия</td>
	</tr>
	${rows}
</table>
<br />
<form action="/trash" method="post">
<input type="hidden" name="action" value="confirm" />
<input type="submit" value="Оформить заказ" />
</form>
<!-- trash_table.inc END -->
