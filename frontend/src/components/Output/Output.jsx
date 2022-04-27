import React from "react";
import './Output.scss'
import { message } from "antd";
import CopyToClipboard from "react-copy-to-clipboard";
import ReturnButton from "../utils/outputButtons/ReturnButton";
import ExportButton from "../utils/outputButtons/ExportButton";
import CopyButton from "../utils/outputButtons/CopyButton";
export default function Output({ display, handleReturn, insertStatements }) {
    const exportRaw = (data, name) => {
        let urlObject = window.URL || window.webkitURL || window;
        let export_blob = new Blob([data]);
        let save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a")
        save_link.href = urlObject.createObjectURL(export_blob);
        save_link.download = name;
        save_link.click();
    }
    const handleStatements = () => {
        let data = ''
        for (let statement of insertStatements) {
            data += (statement + '\n')
        }
        return data
    }
    return (
        <div className="mask" style={{ display }}>
            <div className="card">
                <div className="head">测试数据插入语句：</div>
                <div className="statements">
                    {
                        insertStatements.map((item, key) => {
                            return (
                                <div key={key} className="insertStatement">{item}</div>
                            )
                        })
                    }
                </div>
                <div className="buttons">
                    <div onClick={handleReturn}>
                        <ReturnButton />
                    </div>
                    <CopyToClipboard onCopy={() => message.success('已复制到剪切板')} text={handleStatements()}>
                        <div>
                            <CopyButton />
                        </div>
                    </CopyToClipboard>
                    <div onClick={() => exportRaw(handleStatements(), new Date().getTime() + '.sql')}>
                        <ExportButton />
                    </div>
                </div>
            </div>
        </div>
    )
}