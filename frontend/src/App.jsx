import { Routes, Route, Navigate } from 'react-router-dom';
import Cookies from 'js-cookie';

// pages
import RegisterPage from './pages/RegisterPage';
import AuthPage from './pages/AuthPage';
import PersonalAccountPage from './pages/PersonalAccountPage';

function App() {

  return (
    <div className="App">
      <Routes>
        <Route path="/auth" element={Cookies.get('refresh_token') === undefined ? <AuthPage /> : <Navigate to="/personal-account" />}></Route>
        <Route path="/register" element={<RegisterPage />}></Route>
        <Route path="/personal-account" element={<PersonalAccountPage />}></Route>
        <Route path="/" element={<Navigate to="/auth" />} />
      </Routes>
    </div>
  );
}

export default App;