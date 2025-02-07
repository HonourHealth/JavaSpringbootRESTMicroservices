import React from 'react';
import { render } from '@testing-library/react';
import '@testing-library/jest-dom';

const customRender = (ui, options) => {
    return render(ui, { ...options });
};

export * from '@testing-library/react';
export { customRender as render };