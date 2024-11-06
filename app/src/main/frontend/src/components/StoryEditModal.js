import ReactModal from 'react-modal';
import { StoryAddEditContext } from './StoryEdit';
import { ButtonProvider } from './ButtonProvider';

const StoryEditModal = ({ onSubmit, onClose, shouldCloseOnOverlayClick = true }) => {
    const handleClickSubmit = () => {
        onSubmit();
    };

    const handleClickCancel = () => {
        onClose();
    };

    return (
        <ReactModal
            isOpen
            onRequestClose={onClose}
            shouldCloseOnOverlayClick={shouldCloseOnOverlayClick}
            className="modal modal-right"
            overlayClassName="modal-overlay">

            <div className='modal__hedader'>

                <ButtonProvider width={'icon'}>
                    <button type="button" className={`button button__icon`} onClick={handleClickCancel}>
                        <i data-button="icon" className={`icon icon__arrow__right__black`}></i>
                        <span className={`blind`}>닫기</span>
                    </button>
                </ButtonProvider>
            </div>

            <div className='modal__body'>
                <StoryAddEditContext />
            </div>

            <div className='modal__footer'>
                <ButtonProvider>
                    <button type="button" className={`button button__primary`} onClick={handleClickSubmit}>
                        <span className={`button__text`}>등록</span>
                    </button>
                </ButtonProvider>
            </div>

        </ReactModal>
    );
};

export default StoryEditModal;