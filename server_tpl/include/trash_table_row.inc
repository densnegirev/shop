<!-- trash_table_row.inc BEGIN -->
<tr>
	<td class="preview_small center"><img src="${image}" /></td>
	<td class="center">${model}</td>
	<td class="center">${amount}</td>
	<td class="center">${price}</td>
	<td class="center">
		<form action="/trash" method="post">
            <input type="hidden" name="action" value="delete" />
            <input type="hidden" name="itemid" value="${itemId}" />
            <input type="submit" value="Удалить" />
        </form>
	</td>
</tr>
<!-- trash_table_row.inc END -->
