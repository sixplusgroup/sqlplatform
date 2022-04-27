import './App.scss';
import { useRef, useState } from 'react'
import CodeMirror from '@uiw/react-codemirror';
import { sql } from '@codemirror/lang-sql'
import TableSchema from './components/TableSchema/TableSchema';
import Output from './components/Output/Output';
import SendButton from './components/utils/SendButton/SendButton';
import Loading from './components/utils/loading/Loading';
import { message } from 'antd';
import qs from 'qs'
import axios from 'axios';
import XMLBuilder from './components/utils/XMLBuilder';
import 'antd/dist/antd.css'

const DEFAUTL_NUM_OF_TABLES = 1

function App() {
  const onTableChange = (tableIndex, name, columns) => {
    tables.current[tableIndex - 1] = {
      name,
      columns,
    }
  }
  const [numOfTables, setNumOfTables] = useState(() => DEFAUTL_NUM_OF_TABLES)
  const [tableComponents, setTableComponents] = useState(() => [<TableSchema index={1} onTableChange={onTableChange} />])
  const tables = useRef([{
    name: null,
    columns: null
  }])
  const [inputSql, setInputSql] = useState('')
  const [display, setDisplay] = useState('none')
  const [insertStatements, setStatements] = useState([])
  const [isLoading, setIsLoading] = useState(false)
  const decrement = () => {
    if (numOfTables === 1) {
      alert('至少要有一张表')
      return
    }
    setNumOfTables(prev => prev - 1)
    setTableComponents(prev => prev.slice(0, -1))
    tables.current.pop()
  }
  const increment = () => {
    setTableComponents(prev => [...prev, <TableSchema index={numOfTables + 1} onTableChange={onTableChange} />])
    setNumOfTables(prev => prev + 1)
  }
  // 数据发送前，前端先做一些校验
  const verifyData = () => {
    if (inputSql === '') {
      message.error('ERROR: empty sql query')
      return false
    }
    for (let table of tables.current) {
      if (table.name === '') {
        message.error('ERROR: empty table name exists')
        return false
      }
      for (let column of table.columns) {
        if (column.name === '') {
          message.error('ERROR: empty column name exists')
          return false
        }
      }
    }
    return true
  }
  const handleSubmit = () => {
    if (verifyData()) {
      setDisplay('flex')
      setIsLoading(true)
      axios({
        method: 'POST',
        url: 'http://localhost:8080/tdg',
        data: {
          query: inputSql,
          tables: tables.current,
          coverageRequestXML: XMLBuilder(inputSql, tables.current)
        }
      }).then(response => {
        if (response.status === 200) {
          setIsLoading(false)
          setStatements(response.data)
        }
      })
    }
  }
  return (
    <div className="App">
      <div style={{ filter: display === 'flex' ? 'blur(8px)' : 'none' }}>
        <header className="App-header" >
          SQL 测试数据自动生成系统
        </header>
        <main>
          <div className='input-area'>
            <div className='label'>SQL Query: </div>
            <div className='code-input'>
              <CodeMirror
                className='query-input'
                extensions={[sql()]}
                height={'160px'}
                minWidth={'300px'}
                width={'800px'}
                onChange={(value, viewUpdate) => setInputSql(value)}
              />
            </div>
            <div className='label'>Database Schema: </div>
            <div className='schema'>
              <div className='table-number'>
                <div className='table-number-text' style={{ marginRight: '10px' }}>Number of Tables: </div>
                <button onClick={decrement} className='crement-btn'>-</button>
                <div className='number-text'>{numOfTables}</div>
                <button onClick={increment} className='crement-btn'>+</button>
              </div>
              {
                tableComponents.map((item, key) => {
                  return (
                    <div key={key} className='table-row'>
                      {item}
                    </div>
                  )
                })
              }
              <div className='btns' style={{ marginTop: '18px', width: 'fit-content' }} onClick={handleSubmit}>
                <SendButton />
              </div>
            </div>
          </div>
        </main>
      </div>
      {
        isLoading ?
          <Loading /> :
          <Output display={display} handleReturn={() => {
            setDisplay('none')
          }} insertStatements={insertStatements} />
      }
    </div>
  );
}

export default App;
