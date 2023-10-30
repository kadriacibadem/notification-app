import React from 'react';
import { useState } from 'react';
import axios from 'axios';

function Customer() {
    const [customer, setCustomer] = useState({
        name: '',
        surname: '',
        email: '',
        phone: ''
    });

    const handleChangeCustomerData = (e) => {
        const { id, value } = e.target;
        setCustomer((prevCustomer) => ({
            ...prevCustomer,
            [id]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
            axios.post('http://localhost:8000/customer/create', customer)
                .then(response => {
                    setCustomer({
                        name: '',
                        surname: '',
                        email: '',
                        phone: ''
                    });
                })
                .catch(error => {
                    console.log(error);
                });
    };

    return (
        <div className="container mt-5">
            <h1>Müşteri Ekle</h1>
            <form onSubmit={handleSubmit}>
                <div className="form-group mt-3">
                    <label htmlFor="ad">Adı</label>
                    <input type="text" value={customer.name} className="form-control" onChange={handleChangeCustomerData} id="name" placeholder="Müşteri adını girin" />
                </div>
                <div className="form-group mt-2">
                    <label htmlFor="soyad">Soyadı</label>
                    <input type="text" value={customer.surname} className="form-control" onChange={handleChangeCustomerData} id="surname" placeholder="Müşteri soyadını girin" />
                </div>
                <div className="form-group mt-2">
                    <label htmlFor="email">E-Posta Adresi</label>
                    <input type="email" value={customer.email} className="form-control" onChange={handleChangeCustomerData} id="email" placeholder="E-Posta adresini girin" />
                </div>
                <div className="form-group mt-2">
                    <label htmlFor="telefon">Telefon Numarası</label>
                    <input type="tel" value={customer.phone} className="form-control" onChange={handleChangeCustomerData} id="phone" placeholder="Telefon numarasını girin" />
                </div>
                <button type="submit" className="btn btn-primary mt-4">Müşteriyi Ekle</button>
            </form>
        </div>
    )

}

export default Customer;