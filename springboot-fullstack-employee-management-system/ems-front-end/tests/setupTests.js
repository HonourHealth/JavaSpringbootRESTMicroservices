// jest-dom adds custom jest matchers for asserting on DOM nodes.
// allows you to do things like:
// expect(element).toHaveTextContent(/react/i)
// learn more: https://github.com/testing-library/jest-dom
import '@testing-library/jest-dom';

// Add TextEncoder/TextDecoder for React Router
import { TextEncoder, TextDecoder } from 'util';
globalThis.TextEncoder = TextEncoder;
globalThis.TextDecoder = TextDecoder;

// Mock MutationObserver
globalThis.MutationObserver = class {
  constructor(callback) {}
  disconnect() {}
  observe(element, initObject) {}
};