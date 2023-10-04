import React, { useState, useEffect } from "react";
import axios from "axios";
import {BsArrowDown,BsArrowUp} from "react-icons/bs";

function Mainpage() {
    const [data, setData] = useState([]);
    const [openRowId, setOpenRowId] = useState(null);

    useEffect(() => {
        axios
            .get("http://localhost:8000/message/getall")
            .then((res) => {
                setData(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);
    console.log(data)

    const toggleOpen = (rowId) => {
        setOpenRowId(openRowId === rowId ? null : rowId);
    };

    return (
        <div className="mt-4">
            <div>
                <h1>Tüm Bildirimler</h1>
                <table className="table">
                    <thead>
                        <tr>
                            <th>Gönderici</th>
                            <th>Bildirim Durumu</th>
                            <th>Bildirim Türü</th>
                            <th>Bildirim İçeriği</th>
                            <th>Alıcılar</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.map((item) => (
                            <tr key={item.id}>
                                <td>{item.sender}</td>
                                <td>{item.position}</td>
                                <td>{item.channel}</td>
                                <td>{item.templateEntity.context}</td>
                                <td>
                                <button onClick={() => toggleOpen(item.id)}>
                                        {openRowId === item.id ? <BsArrowUp /> : <BsArrowDown />}
                                    </button>
                                    {openRowId === item.id && (
                                        <ul class="list-group list-group-light">
                                            {item.receiverEntityList.map(
                                                (receiver) => (
                                                    <li key={receiver.id} class="list-group-item border-0">
                                                        {receiver.receiverAddress}
                                                    </li>
                                                )
                                            )}
                                        </ul>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Mainpage;
                                        