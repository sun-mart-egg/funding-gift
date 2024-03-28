import { useState, useEffect } from "react";
import { FiSearch } from "react-icons/fi";

function SearchBar({ setKeyword, onSubmit }) {
  const [searchText, setSearchText] = useState("");

  const handleChange = (e) => {
    const { value } = e.target;
    setSearchText(value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setKeyword(searchText); // Pass searchText as the keyword
    onSubmit(); // Call the submission event
  };

  return (
    <form onSubmit={handleSubmit} className="search relative mx-auto flex max-w-[95%] items-center">
      <input
        type="text"
        name="searchText"
        value={searchText}
        onChange={handleChange}
        className="w-full rounded-md border border-cusColor3 py-1.5 pl-2 pr-10 text-gray-900 placeholder:text-xs placeholder:text-gray-400 focus:border-transparent focus:ring-2 focus:ring-cusColor3"
        placeholder="제품의 이름으로 펀딩을 검색해보세요."
      />
      <button
        type="submit"
        className="absolute inset-y-0 right-0 flex items-center px-3 text-cusColor3"
      >
        <FiSearch size={20} />
      </button>
    </form>
  );
}

export default SearchBar;
