import { useEffect, useRef, useState } from "react"
import { Divider } from "antd"
import Column from "../Column/Column"
import './TableSchema.scss'

const DEFAULT_NUM_OF_COLUMNS = 2
const DataTypeMap = new Map([
    ['int', 'INTEGER'],
    ['varchar', 'VARCHAR'],
    ['bool', 'BOOLEAN'],
    ['time', 'TIME'],
    ['date', 'DATE'],
    ['timestamp', 'TIMESTAMP'],
    ['double', 'DOUBLE'],
    ['null', 'NULL']
])
export default function TableSchema(props) {
    const { index, onTableChange } = props
    const onColumnChange = (columnIndex, name, dataType, isKey, isNullable) => {
        columns.current[columnIndex - 1] = {
            name,
            dataType: DataTypeMap.get(dataType),
            isKey,
            isNullable,
        }
    }
    const [tableName, setTableName] = useState(() => '')
    const [numOfColumns, setNumOfColumns] = useState(() => DEFAULT_NUM_OF_COLUMNS)
    const [columnComponents, setColumnComponent] = useState(() => [
        <Column index={1} onColumnChange={onColumnChange} />,
        <Column index={2} onColumnChange={onColumnChange} />
    ])
    const columns = useRef([{
        name: '',
        dataType: '',
        isKey: false,
        isNullable: false,
    }, {
        name: '',
        dataType: '',
        isKey: false,
        isNullable: false,
    }])
    useEffect(() => {
        onTableChange(index, tableName, columns.current)
    }, [tableName])
    const decrement = () => {
        if (numOfColumns === 1) {
            alert('至少需要一列')
            return
        }
        setNumOfColumns(prev => prev - 1)
        setColumnComponent(prev => prev.slice(0, -1))
        columns.current.pop()
    }
    const increment = () => {
        setColumnComponent(prev => [...prev, <Column index={numOfColumns + 1} onColumnChange={onColumnChange} />])
        setNumOfColumns(prev => prev + 1)
    }
    return (
        <div className="tableSchema">
            <div className="table-head">
                <div className="table-name" style={{ marginRight: '20px' }}>
                    <div className="head-label">Tabel {index} Name: </div>
                    <input
                        className="input table-name-input"
                        onChange={(e) => setTableName(e.target.value)}
                    />
                </div>
                <div className="num-of-columns">
                    <div className="head-label">Number of columns: </div>
                    <button className="crement-btn" onClick={decrement}>-</button>
                    <div className="number-text">{numOfColumns}</div>
                    <button className="crement-btn" onClick={increment}>+</button>
                </div>
            </div>
            <Divider style={{ margin: '12px 0' }} />
            {
                columnComponents.map((item, key) => {
                    return (
                        <div key={key}>
                            {item}
                        </div>
                    )
                })
            }
        </div>
    )
}