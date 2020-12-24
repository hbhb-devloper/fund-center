selectPageByCond
===
```sql
    select 
    -- @pageTag(){
        fc.id                                      as id,
        fc.code                                    as code,
        us.nick_name                               as createMan,
        date_format(fc.create_time, '%Y-%m-%d')    as createTime,
        un.unit_name                               as unitName,
        fc.group_name                              as groupName,
        fc.bus_type                                as busType,
        fc.fund_flows                              as fundFlows,
        fc.amount_type                             as amountType,
        format(fc.amount, 2)                       as amount,
        fc.invoice_amount                          as invoiceAmount,
        fc.invoice_code                            as invoiceCode,
        fc.pre_invoice_amount                      as preInvoiceAmount,
        fc.implementer                             as peopleDown,
        date_format(fc.implement_time, '%Y-%m-%d') as peopleDownTime,
        fc.state                                   as state,
        ifnull(fcf.id, 0)                          as document
    -- @}
    from fund_customer fc
         left join fund_customer_file fcf on fc.id = fcf.customer_id
         left join sys_user us on fc.create_by = us.id
         left join unit un on fc.unit_id = un.id
    -- @where(){
        -- @if(isNotEmpty(list)){
            and fc.unit_id in (#{join(list)})
        -- @}
        -- @if(isNotEmpty(cond.amountType)){
            and fc.amount_type = #{cond.amountType}
        -- @}
        -- @if(isNotEmpty(cond.groupName)){
            and fc.group_name like concat('%', #{cond.groupName}, '%')
        -- @}
        -- @if(isNotEmpty(cond.busType)){
            and fc.bus_type = #{cond.busType}
        -- @}
        -- @if(isNotEmpty(cond.fundFlows)){
            and fc.fund_flows = #{cond.fundFlows}
        -- @}
        -- @if(isNotEmpty(cond.code)){
            and fc.code = #{cond.code}
        -- @}
        -- @if(isNotEmpty(cond.peopleDownTime)){
            and date_format(fc.implement_time, '%Y-%m-%d') = #{cond.peopleDownTime}
        -- @}
        -- @if(isNotEmpty(cond.state)){
            and fc.state = #{cond.state}
        -- @}
        -- @if(isNotEmpty(cond.userName)){
            and us.nick_name like concat('%', #{cond.userName}, '%')
        -- @}
        -- @if(isNotEmpty(cond.fundYear)){
            and date_format(fc.create_time, '%Y-%m-%d') = #{cond.fundYear}
        -- @}
    -- @}
    -- @pageIgnoreTag(){
        order by fc.create_time desc
    -- @}
```

selectStatPageByCond
===
```sql
    select 
    -- @pageTag(){
        t1.id                                            as id,
        concat(#{cond.startTime}, '至', #{cond.endTime}) as periodTime,
        t1.unitName                                      as unitName,
        t1.groupName                                     as groupName,
        t1.createMan                                     as createMan,
        format(t1.beginAmount, 2)                        as beginAmount,
        format(t1.addAmount, 2)                          as addAmount,
        format(t1.verifyAmount, 2)                       as verifyAmount,
        format(t1.reduceAmount, 2)                       as reduceAmount,
        format(t1.collectionFrozen, 2)                   as collectionFrozen,
        format(t1.useFrozen, 2)                          as useFrozen,
        format(t1.refundFrozen, 2)                       as refundFrozen,
        format(t1.balance, 2)                            as balance,
        format(t1.totalEnterAmount, 2)                   as totalEnterAmount,
        format(t1.totalInvoiceAmount, 2)                 as totalInvoiceAmount
    -- @}
    from (
             SELECT distinct fc.id                                                                        as id,
                             u.unit_name                                                                  as unitName,
                             fc.group_name                                                                as groupName,
                             su.nick_name                                                                 as createMan,
                             IFNULL(fcg.account_start, 0) +
                             (SELECT IFNULL(SUM(fc1.amount), 0)
                              FROM fund_customer fc1
                              WHERE fc1.group_name = fc.group_name
                                AND fc1.fund_flows = 2
                                AND fc1.state = 31
                                AND fc1.create_time <= #{cond.startTime}) -
                             (SELECT IFNULL(SUM(fc2.amount), 0)
                              FROM fund_customer fc2
                              WHERE fc2.group_name = fc.group_name
                                AND fc2.fund_flows = 1
                                AND fc2.state = 31
                                AND fc2.create_time <= #{cond.startTime})                                 as beginAmount,
    
                             (SELECT DISTINCT IFNULL(SUM(DISTINCT fc3.amount), 0)
                              FROM fund_customer fc3
                              WHERE fc.group_name = fc3.group_name
                                AND fc3.fund_flows = 2
                                AND fc3.state = 31
                                AND fc3.create_time BETWEEN #{cond.startTime} AND #{cond.endTime})        as addAmount,

                             0                                                                            as verifyAmount,

                             (SELECT DISTINCT IFNULL(SUM(DISTINCT fc4.amount), 0) amount
                              FROM fund_customer fc4
                              WHERE fc.group_name = fc4.group_name
                                AND fc4.fund_flows = 1
                                AND fc4.state = 31
                                AND fc4.create_time BETWEEN #{cond.startTime} AND #{cond.endTime})        as reduceAmount,
    
                             (SELECT IFNULL(SUM(DISTINCT fcv1.amount), 0) amount
                              FROM fund_customer fc5
                                       LEFT JOIN fund_customer_version fcv1 ON fc5.id = fcv1.customer_id
                              WHERE fc5.group_name = fc.group_name
                                AND fc5.fund_flows = 2
                                AND fc5.state = 20
                                AND fc5.create_time BETWEEN #{cond.startTime} AND #{cond.endTime})        as collectionFrozen,
    
                             (SELECT IFNULL(SUM(DISTINCT fcv2.amount), 0) amount
                              FROM fund_customer fc6
                                       LEFT JOIN fund_customer_version fcv2 ON fc6.id = fcv2.customer_id
                              WHERE fc.group_name = fc6.group_name
                                AND fc6.fund_flows = 1
                                AND fc6.state = 20
                                AND fc6.create_time BETWEEN #{cond.startTime} AND #{cond.endTime})        as useFrozen,
    
                             (SELECT IFNULL(SUM(DISTINCT fcv3.amount), 0) amount
                              FROM fund_customer fc7
                                       LEFT JOIN fund_customer_version fcv3 ON fc7.id = fcv3.customer_id
                              WHERE fc.group_name = fc7.group_name
                                AND fc7.fund_flows = 1
                                AND fc7.state = 20
                                AND fc7.create_time BETWEEN #{cond.startTime} AND #{cond.endTime})        as refundFrozen,
    
                             (IFNULL(fcg.account_start, 0) +
                              (SELECT IFNULL(SUM(fc8.amount), 0)
                               FROM fund_customer fc8
                               WHERE fc8.fund_flows = 2
                                 AND fc8.state = 31
                                 AND fc8.create_time <= #{cond.startTime}
                                 AND fc8.group_name = fc.group_name) -
                              (SELECT IFNULL(SUM(fc9.amount), 0)
                               FROM fund_customer fc9
                               WHERE fc9.fund_flows = 1
                                 AND fc9.state = 31
                                 AND fc9.create_time <= #{cond.startTime}
                                 AND fc.group_name = fc9.group_name) +
                              (SELECT DISTINCT IFNULL(SUM(DISTINCT fc10.amount), 0)
                               FROM fund_customer fc10
                               WHERE fc10.fund_flows = 2
                                 AND fc10.state = 31
                                 AND fc10.create_time BETWEEN #{cond.startTime} AND #{cond.endTime}
                                 AND fc.group_name = fc10.group_name) -
                              (SELECT DISTINCT IFNULL(SUM(DISTINCT fc11.amount), 0)
                               FROM fund_customer fc11
                               WHERE fc11.fund_flows = 1
                                 AND fc11.state = 31
                                 AND fc11.create_time BETWEEN #{cond.startTime} AND #{cond.endTime}
                                 AND fc.group_name = fc11.group_name))                                    as balance,
    
                             (IFNULL(fcg.account_start, 0) +
                              (SELECT IFNULL(SUM(fc12.amount), 0) amount
                               FROM fund_customer fc12
                               WHERE fc.group_name = fc12.group_name
                                 AND fc12.fund_flows = 2
                                 AND fc12.state = 31
                                 AND fc12.create_time <= #{cond.startTime}))                              as totalEnterAmount,
    
                             (SELECT ifnull(SUM(fc13.invoice_amount), 0) +
                                     ifnull(SUM(fc13.pre_invoice_amount), 0)
                              FROM fund_customer fc13
                              WHERE fc13.group_name = fc.group_name
                                AND fc13.state = 31
                                AND fc13.create_time <= #{cond.startTime}) + IFNULL(fcg.Invoice_start, 0) as totalInvoiceAmount
             FROM fund_customer fc
                      LEFT JOIN unit u ON fc.unit_id = u.id
                      LEFT JOIN fund_customer_group fcg ON fc.group_name = fcg.group_name
                      LEFT JOIN sys_user su ON su.id = fc.create_by
             where fc.state = 31
               AND fc.implement_time != ''
               AND fc.create_time BETWEEN #{cond.startTime} AND #{cond.endTime}
                -- @if(isNotEmpty(list)){
                    and fc.unit_id in (#{join(list)})
                -- @}
                -- @if(isNotEmpty(cond.amountType)){
                    and fc.amount_type = #{cond.amountType}
                -- @}
                -- @if(isNotEmpty(cond.busType)){
                    and fc.bus_type = #{cond.busType}
                -- @}
                -- @if(isNotEmpty(cond.groupName)){
                    and fc.group_name like concat('%', #{cond.groupName}, '%')
                -- @}
                -- @if(isNotEmpty(cond.userName)){
                    and su.nick_name like concat('%', #{cond.userName}, '%')
                -- @}
             group by fc.group_name
         ) t1
    -- @where(){
        -- @if(isNotEmpty(cond.isBalanceZero)){
            and balance != 0
        -- @}
    -- @}
```