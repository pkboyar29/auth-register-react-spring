import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Cookies from 'js-cookie';

function PersonalAccountPage() {

	const [firstName, setFirstName] = useState('')
	const [theme, setTheme] = useState('light')

	const navigate = useNavigate()

	useEffect(() => {
		if (Cookies.get('userId') !== undefined) {
			fetch('http://127.0.0.1:8080/api/users/' + Cookies.get('userId'), {
				method: 'GET'
			})
				.then(response => {
					switch (response.status) {
						case 200:
							return response.json()
								.then(responseBody => {
									console.log(responseBody)
									setFirstName(responseBody['body']['first_name'])
									setTheme(responseBody['body']['theme'])
								})
						default:
							return response.text()
								.then(responseBody => console.log(responseBody))
					}
				})
		}
	}, [])

	const onLogOutHandle = () => {
		setFirstName('')
		setTheme('light')
		Cookies.remove('userId')
		navigate('/auth')
	}

	const handleChangeTheme = () => {
		const newTheme = theme === 'light' ? 'dark' : 'light';

		fetch('http://127.0.0.1:8080/api/users/' + Cookies.get('userId') + '/theme', {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ theme: newTheme })
		})
			.then(response => {
				switch (response.status) {
					case 200:
						setTheme(newTheme)
						return
					default:
						return response.text()
							.then(responseText => console.log(responseText))
				}
			})
	}

	const changeThemeButtonText = theme === 'light' ? 'Светлая тема' : 'Темная тема';

	return (
		<div className={theme === 'dark' ? 'darkBackground' : ''}>
			{Cookies.get('userId') !== undefined ? (
				<>
					<div className={"header " + (theme === 'dark' ? "darkHeader" : '')}>
						<div onClick={handleChangeTheme} className={"header__theme " + (theme === 'dark' ? "darkButton" : '')}>{changeThemeButtonText}</div>
						<div onClick={onLogOutHandle} className={"header__exit " + (theme === 'dark' ? "darkButton" : '')}>Выйти</div>
					</div>
					<div className={"text " + (theme === 'dark' ? 'darkText' : '')}>Добро пожаловать в личный кабинет, {firstName}!</div>
				</>
			) : (
				<p className="notAuthText">Вы не авторизованы. <Link to="/auth">Авторизоваться.</Link></p>
			)}
		</div>
	)
}

export default PersonalAccountPage