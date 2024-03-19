function SearchBar({ })  {
    return (
        <form className="search border-solid ">
          <input
          type="text"
          name="searchText"
          className="block w-full rounded-md border-2 py-1.5 pl-7 pr-20 text-gray-900 ring-1 ring-inset ring-cusColor3 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
          placeholder="제품을 검색해보세요."
        />
        </form>
    );
}
export default SearchBar
