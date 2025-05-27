import logo from './logo.svg';
import './App.css';
import { useState } from 'react';
 
const content = {
    Home: 'Welcome to the Home page.',
    About: 'This is the About page.',
    Services: 'Here we offer various services.',
    Contact: 'Reach us through the Contact page.',
  };


function App() {
  const [selected, setSelected] = useState('Home');
  return (
    <div>
      <nav>
        <button onClick={() => setSelected('Home')}>Home</button>
        <button onClick={() => setSelected('About')}>About</button>
        <button onClick={() => setSelected('Services')}>Services</button>
        <button onClick={() => setSelected('Contact')}>Contact</button>
      </nav>

      <div>
        <p>{content[selected]}</p>
      </div>
    </div>
  );
}

export default App;
