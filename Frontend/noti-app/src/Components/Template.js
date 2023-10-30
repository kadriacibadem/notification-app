import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import axios from 'axios';
function Template(props) {
    const [exclusionStartTime, setExclusionStartTime] = useState();
    const [exclusionEndTime, setExclusionEndTime] = useState();

    const convertToLocalDate = (date) => {
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const year = date.getFullYear();
        return `${day}-${month}-${year}`;
      };

      const handleStartDateChange = (date) => {
        setExclusionStartTime(date);
        const localStartDate = convertToLocalDate(date);
        setFormData({ ...formData, exclusionStartTime: localStartDate });
      };
      const handleEndDateChange = (date) => {
        setExclusionEndTime(date);
        const localEndDate = convertToLocalDate(date);
        setFormData({ ...formData, exclusionEndTime: localEndDate });
      };


    const [formData, setFormData] = useState({
        name: '',
        category: '',
        context: '',
        exclusionStartTime: '',
        exclusionEndTime: ''
    });

    const handleChangeFormData = (event) => {
        setFormData({
            ...formData,
            [event.target.name]: event.target.value
        });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
             const response = await axios.post(
                'http://localhost:8000/template/create',
                formData
            ); 
            console.log('Başarılı:', response.data);
            setFormData({
                name: '',
                category: '',
                context: '',
                exclusionStartTime: '',
                exclusionEndTime: ''
            });

        } catch (error) {
            console.error('Hata:', error);
        }
    };

    const ipAdress = async () => {
        try {
            const response = await axios.get('https://api.ipify.org?format=json');
            const newFormData = { ...formData, ip: response.data.ip };
            setFormData(newFormData);
        } catch (error) {
            console.error('Hata:', error);
        }
    };

    useEffect(() => {
        ipAdress();
    }, []);


    return (
        <div className="container mt-3">
            <h1>Template Oluştur</h1>
            <br />
            <form onSubmit={handleSubmit}>
                <div className="mb-2">
                    <label htmlFor="textarea" className="form-label">Template Adı</label>
                    <input value={formData.name} required onChange={handleChangeFormData} type="text" className="form-control" id="input1" placeholder="Template adını giriniz" name="name" />
                </div>
                <div className="mb-2">
                    <label htmlFor="input2" className="form-label">Template Türü</label>
                    <div className="form-check">
                        <input
                            required
                            className="form-check-input"
                            type="radio"
                            name="category"
                            id="flexRadioDefault1"
                            value="SMS"
                            checked={formData.category === "SMS"}
                            onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                        />
                        <label className="form-check-label" htmlFor="flexRadioDefault1">
                            SMS
                        </label>
                    </div>
                    <div className="form-check">
                        <input
                            required
                            className="form-check-input"
                            type="radio"
                            name="category"
                            id="flexRadioDefault2"
                            value="Mail"
                            checked={formData.category === "Mail"}
                            onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                        />
                        <label className="form-check-label" htmlFor="flexRadioDefault2">
                            Mail
                        </label>
                    </div>
                    <div className="form-check">
                        <input
                            required
                            className="form-check-input"
                            type="radio"
                            name="category"
                            id="flexRadioDefault2"
                            value="Notification"
                            checked={formData.category === "Notification"}
                            onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                        />
                        <label className="form-check-label" htmlFor="flexRadioDefault2">
                            Notification
                        </label>
                    </div>
                </div>
                <div className="mb-2">
                    <label htmlFor="textarea" className="form-label">Template</label>
                    <textarea value={formData.context} required onChange={handleChangeFormData} className="form-control" id="textarea" rows="4" placeholder="Template giriniz" name="context"></textarea>
                </div>
                <div className='row'>
                    <div className='col-md-2'>
                        <DatePicker
                            selected={exclusionStartTime}
                            onChange={handleStartDateChange}
                            dateFormat="dd-MM-yyyy"
                            placeholderText='Başlama Tarihini Seçiniz'
                            required
                            withPortal
                            minDate={new Date()}
                        />
                    </div>
                    <div className='col-md-2'>
                        <DatePicker
                            selected={exclusionEndTime}
                            onChange={handleEndDateChange}
                            dateFormat="dd-MM-yyyy"
                            placeholderText='Bitiş Tarihini Seçiniz'
                            required
                            withPortal
                            minDate={exclusionStartTime ? exclusionStartTime : new Date()}
                        />
                    </div>
                    <div className='col-md-2'>
                        <button type="submit" className="btn btn-primary">Gönder</button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default Template;