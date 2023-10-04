import React, { useState, useEffect } from "react";
import axios from "axios";
import { TimePicker } from 'react-ios-time-picker';

function Notification() {
    const [templates, setTemplates] = useState([]);
    const [customers, setCustomers] = useState([]);
    const [selectedTemplate, setSelectedTemplate] = useState(null);
    const [selectedCustomers, setSelectedCustomers] = useState([]);
    const [selectAllCustomers, setSelectAllCustomers] = useState(false);
    const [checkSetTime, setCheckSetTime] = useState(false);
    const [fromTime,setFromTime ] = useState();
    const [toTime,setToTime ] = useState();
    const [serverData , setServerData] = useState({
        templateId : "",
        customers : [],
        fromTime : fromTime,
        toTime : toTime,
        sender: "kadriacibadem@gmail.com"
    });


    const handleCustomerClick = (customer) => {
        if (selectedCustomers.includes(customer)) {
            setSelectedCustomers(selectedCustomers.filter((c) => c !== customer));
            setServerData({ ...serverData, customers: selectedCustomers.filter((c) => c !== customer) });
        } else {
            setSelectedCustomers([...selectedCustomers, customer]);
            setServerData({ ...serverData, customers: [...selectedCustomers, customer]});
        }
    };

    const handleTemplateSelect = (id) => {
        if (selectedTemplate === id) {
            setSelectedTemplate(null);
        } else {
            setSelectedTemplate(id);
            setServerData({ ...serverData, templateId: id });
        }
    };
    const handleSelectAll = () => {
        setSelectAllCustomers((prevSelectAll) => !prevSelectAll);
      };

    const handleSelectAllCustomers = () => {
        if (!selectAllCustomers) {
            setSelectedCustomers(customers);
            setSelectAllCustomers(false);
            setServerData({ ...serverData, customers: customers });
        } else {
            setSelectedCustomers([]);
            setSelectAllCustomers(true);
            setServerData({ ...serverData, customers: [] });
        }
    };
    const checkedSetTime = () => {
        if (!checkSetTime) {
            setCheckSetTime(true);
        } else {
            setCheckSetTime(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setServerData({ ...serverData, fromTime: fromTime, toTime: toTime });
        if(serverData.templateId === "" || serverData.customers.length===0){
            alert("Lütfen tüm alanları doldurunuz.")
            return;
        }
        console.log(serverData)
         try{
            const response = await axios.post(
                'http://localhost:8000/message/create',
                serverData
            );
            console.log('Başarılı:', response.data);
        }catch(error){
            console.error('Hata:', error);
        }
        setSelectedCustomers([]);
        setSelectedTemplate(null);
        setSelectAllCustomers(false);
        setCheckSetTime(false);
        setFromTime();
        setToTime();
        const checkbox = document.getElementById("checkTime");
        checkbox.checked = false;
        setServerData({
            templateId: "",
            customers: [],
            fromTime: null, 
            toTime: null,
            sender: "kadriacibadem@gmail.com",
        });  
    };

    useEffect(() => {
        axios
            .get("http://localhost:8000/template/getall")
            .then((res) => {
                setTemplates(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
        axios
            .get("http://localhost:8000/customer/all")
            .then((res) => {
                setCustomers(res.data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);
    return (
        <div className="container">
            <div className="row">
                <div className="col-md-9" >
                    <h3 className="text-center">Bildirim Şablonları</h3>
                    <div style={{ overflow: 'auto', maxHeight: '300px' }}>
                        <table className="table table-light table-hover shadow">
                            <thead>
                                <tr>
                                    <th style={{ width: "15%" }}>Şablon Adı</th>
                                    <th style={{ width: "70%", textAlign: 'center' }}>İçerik</th>
                                    <th style={{ width: "15%" }}>Bildirim Türü</th>
                                </tr>
                            </thead>
                            <tbody className="required">
                                {templates.map((template) => (
                                    <tr
                                        key={template.id}
                                        className={selectedTemplate === template.id ? 'table-primary' : ''}
                                        onClick={() => handleTemplateSelect(template.id)}
                                    >
                                        <td style={{ maxHeight: "15%" }}>{template.name}</td>
                                        <td style={{ textAlign: "center" }}>{template.context}</td>
                                        <td>{template.category}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
                <div className="col-md-3">
                    <h3 className="text-center">Müşteri Listesi</h3>
                    <ul className="list-group overflow-auto shadow" style={{ maxHeight: "300px" }}>
                        {customers.map((customer) => (
                            <li
                                key={customer.id}
                                className={`list-group-item ${selectedCustomers.includes(customer) ? 'active' : ''
                                    }`}
                                onClick={() => handleCustomerClick(customer)}
                            >
                                {customer.name} {customer.surname}
                            </li>
                        ))}
                    </ul>
                    <div className="form-check">
                        <input
                            type="checkbox"
                            className="form-check-input"
                            id="selectAll"
                            checked={selectAllCustomers}
                            onChange={handleSelectAll}
                            onClick={handleSelectAllCustomers}
                        />
                        <label className="form-check-label" htmlFor="selectAll">
                            Tümünü Seç
                        </label>
                    </div>

                </div>
            </div>
            <div className="row mt-2">
                <div className="col-md-3">
                    <div className="form-check">
                        <input type="checkbox" className="form-check-input" id="checkTime" onClick={checkedSetTime}></input>
                        <label className="form-check-label" htmlFor="checkTime">Bildirim zamanı seç</label>
                    </div>
                </div>
                <div className="col-md-3">
                        <TimePicker
                            onChange={setFromTime}
                            value={fromTime}
                            saveButtonText="Kaydet"
                            cancelButtonText="İptal"
                            placeHolder="Başlama Zamanı"
                            {...checkSetTime ? "" : { disabled: true }}
                        />                 
                </div>
                <div className="col-md-3">
                <TimePicker
                            onChange={setToTime}
                            value={toTime}
                            saveButtonText="Kaydet"
                            cancelButtonText="İptal"
                            placeHolder="Bitiş Zamanı"
                            {...checkSetTime ? "" : { disabled: true }}
                        /> 
                </div>
                <div className="col-md-3">
                    <button className="btn btn-primary" type="submit" onClick={handleSubmit}>Gönder</button>
                </div>
            </div>
        </div>
    )
}


export default Notification;