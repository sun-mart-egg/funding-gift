async function createFunding(formData, token) {
  const currentDateTime = new Date().toISOString();
  const modifiedFormData = {
    ...formData,
    startDate: formData.startDate.toISOString(),
    endDate: formData.endDate.toISOString(),
    anniversaryDate: currentDateTime,
  };

  const response = await fetch(
    import.meta.env.VITE_BASE_URL + "/api/fundings",
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(modifiedFormData),
    },
  );

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  return await response.json();
}

export { createFunding };
