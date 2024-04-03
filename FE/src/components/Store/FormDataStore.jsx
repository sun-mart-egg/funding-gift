// formDataStore.js
import create from "zustand";

const useFormDataStore = create((set) => ({
  formData: {
    minPrice: null,
    targetPrice: null,
    anniversaryDate: "",
    startDate: "",
    endDate: "",
    title: "",
    content: "",
    accountBank: "",
    accountNo: "",
    name: "",
    phoneNumber: "",
    defaultAddr: "",
    detailAddr: "",
    zipCode: "",
    isPrivate: false,
    anniversaryCategoryId: null,
    productId: null,
    productOptionId: null,
  },

  // formData 상태를 업데이트하는 함수
  updateFormData: (key, value) =>
    set((state) => ({
      formData: {
        ...state.formData,
        [key]: value,
      },
    })),

  // 상태 초기화 함수
  resetFormData: () =>
    set({
      formData: {
        minPrice: null,
        targetPrice: 760000,
        anniversaryDate: "",
        startDate: "",
        endDate: "",
        title: "",
        content: "",
        accountBank: "",
        accountNo: "",
        name: "",
        phoneNumber: "",
        defaultAddr: "",
        detailAddr: "",
        zipCode: "",
        isPrivate: true,
        anniversaryCategoryId: null,
        productId: 1,
        productOptionId: 1,
      },
    }),
}));

export default useFormDataStore;
