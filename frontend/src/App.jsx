import { useState } from 'react'
import './App.css'

function App() {
  const [formData, setFormData] = useState({
    nombre: '',
    email: '',
    recordatorio: false
  })

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    console.log('Datos enviados:', formData)
    // Aquí podrías añadir lógica para enviar los datos
  }

  return (
    <div className='formulario'>
      <h1>Agendamiento de cita</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="nombre">Nombre:</label>
        <input 
          type="text" 
          id="nombre" 
          name="nombre" 
          value={formData.nombre}
          onChange={handleChange}
        />
        
        <label htmlFor="email">Correo electrónico:</label>
        <input 
          type="email" 
          id="email" 
          name="email" 
          value={formData.email}
          onChange={handleChange}
        />
        
        <label htmlFor="recordatorio">Confirmación de recordatorio</label>
        <input 
          type="checkbox" 
          id="recordatorio" 
          name="recordatorio" 
          checked={formData.recordatorio}
          onChange={handleChange}
        />
        
        <button type="submit">Enviar</button>
      </form>
    </div>
  )
}

export default App