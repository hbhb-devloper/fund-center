selectPageByCond
===
```sql
    select
    -- @pageTag(){
        fin.id                                            as id,
        fin.invoice_id                                    as invoiceId,
        fin.content                                       as content,
        ROUND(fi.invoice_amount, 2)                       as amount,
        fin.flow_type_id                                  as flowTypeId,
        fi.client_manager                                 as userName,
        fi.unit_id                                        as unitId,
        date_format(fin.create_time, '%Y-%m-%d %H:%i:%s') as createTime,
        fi.state                                          as state
    -- @}
    from fund_invoice_notice fin
         left join fund_invoice fi on fin.invoice_id = fi.id
    where fin.state = 0
    -- @if(isNotEmpty(cond.userId)){
        and fin.receiver = #{cond.userId}
    -- @}
    -- @if(isNotEmpty(cond.unitNum)){
        and fi.unit_number like concat('%', #{cond.unitNum}, '%')
    -- @}
    -- @if(isNotEmpty(cond.amountMin)){
        and fi.invoice_amount >= #{cond.amountMin}
    -- @}
    -- @if(isNotEmpty(cond.amountMax)){
        and fi.invoice_amount <= #{cond.amountMax}
    -- @}
    -- @pageIgnoreTag(){
        order by fin.create_time desc, fin.state
    -- @}
```
