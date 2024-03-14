import React from "react"
import { BrowserRouter, Routes, Route } from "react-router-dom"

import Product from "./components/Products/Product";

  function App() {

    return (
      <>
        <BrowserRouter>
          <Routes>
            <Route path="/product" element={<Product />} />
          </Routes>
        </BrowserRouter>
      </>
    )
  }

export default App
