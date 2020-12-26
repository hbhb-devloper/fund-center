selectPageByCond
===
```sql
    select
    -- @pageTag(){
        fi.id                                           as id,
        u.unit_name                                     as invoiceUnit,
        fi.client_manager                               as clientManager,
        format(fi.invoice_amount, 2)                    as invoiceAmount,
        fi.unit_number                                  as unitNumber,
        fi.unit_name                                    as unitName,
        fi.invoice_content                              as invoiceContent,
        fi.business                                     as business,
        fi.versions                                     as versions,
        fi.invoice_number                               as invoiceNumber,
        date_format(fi.arrearage_month, '%Y-%m')        as arrearageMonth,
        format(fi.arrearage_money, 2)                   as arrearageMoney,
        fi.invoice_user                                 as invoiceUser,
        fi.invoice_create_time                          as invoiceCreateTime,
        fi.state                                        as state,
        date_format(fi.account_time, '%Y-%m-%d')        as accountTime,
        format(fi.account_money, 2)                     as accountMoney,
        fi.invoice_account                              as invoiceAccount,
        fi.is_cancellation                              as isCancellation,
        fi.billing_number                               as billingNumber,
        count(fif.id) > 0                               as isFile
    -- @}
    from fund_invoice fi
        left join fund_invoice_file fif on fi.id = fif.invoice_id
        left join unit u on fi.unit_id = u.id
    where fi.delete_flag = 1
    -- @if(isNotEmpty(list)){
        and fi.unit_id in (#{join(list)})
    -- @}
    -- @if(isNotEmpty(cond.invoiceAmount)){
        and fi.invoice_amount = #{cond.invoiceAmount}
    -- @}
    -- @if(isNotEmpty(cond.business)){
        and fi.business = #{cond.business}
    -- @}
    -- @if(isNotEmpty(cond.state)){
        and fi.state = #{cond.state}
    -- @}
    -- @if(isNotEmpty(cond.clientManager)){
        and fi.client_manager = #{cond.clientManager}
    -- @}
    -- @if(isNotEmpty(cond.isCancellation)){
        and fi.is_cancellation = #{cond.isCancellation}
    -- @}
    -- @if(isNotEmpty(cond.unitNumber)){
        and fi.unit_number = #{cond.unitNumber}
    -- @}
    -- @if(isNotEmpty(cond.unitName)){
        and fi.unit_name like concat('%', #{cond.unitName}, '%')
    -- @}
    -- @if(isNotEmpty(cond.invoiceNumber)){
        and fi.invoice_number = #{cond.invoiceNumber}
    -- @}
    -- @pageIgnoreTag(){
        group by fi.id, fi.create_time
        order by fi.create_time desc
    -- @}
```
