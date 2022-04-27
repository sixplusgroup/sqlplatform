const columnMap = new Map([
    ['INTEGER', 'int'],
    ['VARCHAR', 'varchar'],
    ['BOOLEAN', 'boolean'],
    ['TIMESTAMP', 'timestamp'],
    ['TIME', 'time'],
    ['DATE', 'date'],
    ['DOUBLE', 'double'],
    ['NULL', 'null']
])

const XMLBuilder = (query, tables) => {
    let res = `<body><sql>${query}</sql><schema>`;
    for (const table of tables) {
        res += `<table name="${table.name}">`
        for (const column of table.columns) {
            res += `<column name="${column.name}" type="${columnMap.get(column.dataType)}" notnull="${column.isNullable}" key="${column.isKey}" />`
        }
        res += '</table>'
    }
    res += '</schema></body>'
    return res
}
export default XMLBuilder