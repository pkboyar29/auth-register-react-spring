import { useState, useRef } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate, Link } from 'react-router-dom';
import ReCAPTCHA from 'react-google-recaptcha';
import Cookies from 'js-cookie';

function AuthPage() {

   // Хук useState
   const [showPassword, setShowPassword] = useState(false);
   const [captchaPassed, setCaptchaPassed] = useState(false)

   const togglePasswordVisibility = () => {
      setShowPassword(!showPassword);
   };

   // Хук useNavigate
   const navigate = useNavigate()

   // Хук useForm
   const {
      register,
      setError,
      formState: { errors, isValid },
      handleSubmit
   } = useForm({
      mode: "onBlur"
   });

   const recaptchaRef = useRef()

   const onSubmit = (data) => {
      fetch('http://127.0.0.1:8080/api/users/auth', {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json'
         },
         body: JSON.stringify(data)
      })
         .then(response => {
            switch (response.status) {
               case 200:
                  return response.json()
                     .then(responseBody => {
                        console.log(responseBody)
                        Cookies.set('userId', responseBody["body"])
                        navigate('/personal-account')
                     })
               case 409:
                  return response.json()
                     .then(responseBody => {
                        console.log(responseBody)
                        if (responseBody["error_code"] === "DUPLICATE_USERNAME") {
                           setError('username', {
                              type: 'manual',
                              message: 'Пользователя с таким логином не существует'
                           })
                           recaptchaRef.current.reset()
                           setCaptchaPassed(false)
                        }
                        if (responseBody["error_code"] === "INVALID_PASSWORD") {
                           setError('password', {
                              type: 'manual',
                              message: 'Неверный пароль'
                           })
                           recaptchaRef.current.reset()
                           setCaptchaPassed(false)
                        }
                     })
               default:
                  return response.json()
                     .then(responseBody => {
                        console.log(responseBody)
                     })
            }
         })
   }

   const onChangeCaptcha = (value) => {

      fetch('http://127.0.0.1:8080/api/captcha/verify-token', {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json'
         },
         body: JSON.stringify({ 'token': value })
      })
         .then(response => {
            switch (response.status) {
               case 200:
                  setCaptchaPassed(true)
                  return
               case 400:
                  setCaptchaPassed(false)
                  return
               default:
                  return
            }
         })
   }

   return (

      <div style={{ marginTop: '50px' }}>
         {/* функция handleSubmit принимает как параметр callback функцию */}
         <form onSubmit={handleSubmit(onSubmit)} className="form">

            <div className="form__title">Форма авторизации</div>

            {/* Username */}
            <label>
               <div className="label">
                  <div className="label__title">Логин</div>
                  <input
                     {...register('username', {
                        required: 'Логин не указан'
                     })}
                     type="text"
                     autoComplete="off"
                  />

               </div>

               <div id="label_login" style={{ height: 40 }}>
                  {errors?.username && <p className="error">{errors?.username?.message || "Error!"}</p>}
               </div>
            </label>

            {/* Password */}
            <label>
               <div className="password__input">
                  <div>Пароль</div>

                  <div className="password__field">
                     <input
                        {...register('password', {
                           required: 'Пароль не указан'
                        })}
                        type={showPassword ? 'text' : 'password'}
                        autoComplete="off"
                     />

                     <img className="show" onClick={togglePasswordVisibility} src={showPassword ? "/img/eye.svg" : "/img/eye-off.svg"} />
                  </div>

               </div>

               <div id="label_password" style={{ height: 40 }}>
                  {errors?.password && <p className="error">{errors?.password?.message || "Error!"}</p>}
               </div>
            </label>

            {/* reCAPTCHA v2 */}
            <ReCAPTCHA
               ref={recaptchaRef}
               className="captcha"
               sitekey="6LdxW4gpAAAAAMfJ5sANc6u5HMmxZ5TBKIKoBkTg"
               onChange={onChangeCaptcha}
            />

            {/* SUBMIT BUTTON */}
            <input className="submit_button" type="submit" value="Авторизоваться" disabled={!isValid || !captchaPassed} />


            <div className="switch">
               <div>Еще не зарегестрированы?</div>
               <Link to="/register" className="link">Зарегестрироваться.</Link>
            </div>

         </form>
      </div>
   )
}

export default AuthPage;