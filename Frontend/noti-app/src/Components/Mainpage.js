import React, { useState, useEffect } from "react";
import axios from "axios";

function Mainpage() {
    const [data, setData] = useState([]);

    useEffect(() => {
        axios
            .get("http://localhost:8000/bank/getall")
            .then((res) => {
                setData(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

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
                            
                        </tr>
                    </thead>
                    <tbody>
                        {data.map((item) => (
                            <tr key={item.id}>
                                <td>{item.sender}</td>
                                <td>{item.position}</td>
                                <td>{item.channel}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Mainpage;