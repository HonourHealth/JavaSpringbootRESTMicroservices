import React from 'react'

const HeaderComponent = () => {
  return (
    <div>
        <header>
            <nav className="navbar navbar-expand-md navbar-light bg-secondary justify-content-center">
                <a href='http://localhost:3000' className="navbar-brand" style={{fontSize: '1.5rem'}}>Todo Management App</a>
            </nav>
        </header>
    </div>
  )
}

export default HeaderComponent