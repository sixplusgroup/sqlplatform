import React, { useEffect, useState } from 'react'
import { Select, Checkbox } from "antd"
import './Column.scss'
const { Option } = Select
export default function Column({index, onColumnChange}) {
    const [columnName, setColumnName] = useState('')
    const [type, setType] = useState('int')
    const [isKey, setIsKey] = useState(false)
    const [isNotNull, setIsNotNull] = useState(false)
    useEffect(() => {
        onColumnChange(index, columnName, type, isKey, isNotNull)
    }, [columnName, type, isKey, isNotNull])
    return (
        <div className='column'>
            <div className='column-label' style={{marginLeft: '0'}}>Column {index} name: </div>
            <input className="input column-name-input" onChange={(e) => setColumnName(e.target.value)} />
            <div className='column-label'>type</div>
            <Select
                defaultValue={'int'} 
                className={'type-select'}
                onChange={(value) => setType(value)}
                size={'small'}
            >
                <Option value={'int'} />
                <Option value={'varchar'} />
                <Option value={'bool'} />
            </Select>
            <div className='column-label'>key</div>
            <Checkbox onChange={() => setIsKey(prev => !prev)} />
            <div className='column-label'>not null</div>
            <Checkbox onChange={() => setIsNotNull(prev => !prev)} />
        </div>
    )
}